# Spring MVC — `addAttribute()` vs `addFlashAttribute()`

> Personal revision notes. Context: Vanilla Spring 5.3.39 MVC, no Spring Boot, no Maven/Gradle,
> Eclipse Dynamic Web Project, Servlet 3.1, Java 8.

---

## Why I kept getting confused

Both methods live on the same `RedirectAttributes` interface. Both get called right before a
`redirect:`. So my brain kept lumping them together as "stuff for redirects". They're not the
same thing at all — they solve two unrelated problems:

- `addAttribute()` → puts data **into the redirect URL**
- `addFlashAttribute()` → puts data **into a server-side stash**, never into the URL

Once that clicked, everything else made sense.

---

## `addAttribute(name, value)` — URL data

When I write:

```java
return "redirect:/accounts/{id}";
```

Spring scans that string for `{placeholder}` tokens and fills them in, checking in this order:

1. Path variables from the **current** request (this happens automatically — I don't need to
   call `addAttribute()` for these at all)
2. Values I manually added via `redirectAttrs.addAttribute(...)`
3. Model attributes (legacy behavior, don't rely on this)

If a value I `addAttribute()`'d doesn't match any `{placeholder}` in the URL string, Spring
doesn't discard it — it just tacks it on as a query param instead (`?id=5`).

**Properties to remember:**

- Ends up **visible in the browser address bar**.
- Because it's baked into the URL, hitting **refresh re-sends the same GET** — safe and repeatable.
- Values get `toString()`'d and URL-encoded → only good for **simple scalars** (ids, slugs,
  page numbers). Not for objects.
- Basically a safer/cleaner alternative to manually string-concatenating the redirect URL.

---

## `addFlashAttribute(name, value)` — one-time server-side data

This does **not** touch the URL at all.

Instead, Spring stores the object in a `FlashMap`. By default (`SessionFlashMapManager`), this
gets stashed in the `HttpSession`. On the **very next incoming request** (matched by
path/params), `DispatcherServlet` automatically:

1. Pulls the matching `FlashMap` out of storage
2. Merges its contents straight into the `Model` of whatever controller handles that next request
3. **Deletes it** — strictly one-time use

**Properties to remember:**

- **Never appears in the URL** → not bookmarkable, invisible to the user.
- Survives exactly **one redirect hop**, then gone. If the destination page is refreshed, the
  flash data does *not* come back. (This is *the* fix for "success message reappears on page
  refresh" bugs.)
- Can hold **any object** — string, list, whole `BindingResult`, whatever — since it's never
  serialized into a URL.
- This is the standard Spring mechanism for **Post → Redirect → Get flash messages**
  ("Account created!", "Item deleted!", etc.)

---

## Side-by-side

| | `addAttribute()` | `addFlashAttribute()` |
|---|---|---|
| Where it lives | Redirect URL (path var or query param) | Server-side (`HttpSession`-backed `FlashMap`) |
| Visible to client? | Yes — in address bar | No |
| Survives page refresh? | Yes | No — one-time only |
| Allowed data types | Simple scalars (gets stringified) | Any object |
| Typical use case | Passing an id to identify the next resource | One-time flash messages, form errors, transient data |

---

## Reference example from the docs

```java
@RequestMapping(value = "/accounts", method = RequestMethod.POST)
public String handle(Account account, BindingResult result, RedirectAttributes redirectAttrs) {
    if (result.hasErrors()) {
        return "accounts/new";
    }
    // Save account ...
    redirectAttrs.addAttribute("id", account.getId())
                 .addFlashAttribute("message", "Account created!");
    return "redirect:/accounts/{id}";
}
```

Walking through it:

- `id` → substituted into `{id}` in the redirect URL → browser lands on `/accounts/42`, and
  `42` is visible in the address bar.
- `message` → nowhere in the URL → carried silently server-side → automatically shows up as
  `${message}` in the model when `/accounts/42`'s GET handler renders its view → gone if the
  page is refreshed.

Also worth remembering — this bit from the docs:

> URI template variables from the **present** request are automatically made available when
> expanding a redirect URL, without needing `Model` or `RedirectAttributes` at all.

i.e. if the incoming request already had `{path}` as a path variable, I don't need to
re-add it manually for the redirect to reuse it.

---

## My own code — before and after

### Before (works, but bypasses Spring's mechanism)

```java
@PostMapping("/tasks")
public String createTask(@ModelAttribute Task task) {
    // ... populate linkedProjName / assigneeName ...
    service.createTask(task);
    return "redirect:/tasks-app/tasks/" + task.getTaskId();
}
```

Manual string concatenation. No URL-encoding safety, no template placeholders, and definitely
no way to pass a one-time success message without polluting the URL with something like
`?msg=Task+created`.

### After (idiomatic — using both methods for what they're meant for)

```java
@PostMapping("/tasks")
public String createTask(@ModelAttribute Task task, RedirectAttributes redirectAttrs) {
    // ... populate linkedProjName / assigneeName ...

    service.createTask(task);

    redirectAttrs.addAttribute("taskId", task.getTaskId());                    // -> goes into the URL
    redirectAttrs.addFlashAttribute("message", "Task created successfully!");  // -> one-time, invisible

    return "redirect:/tasks-app/tasks/{taskId}";
}

@GetMapping("/tasks/{taskId}")
public String viewTask(@PathVariable Long taskId, Model model) {
    // "message" is already in the Model automatically if it was flashed.
    // I don't need to fetch it manually — Spring merges it in before this method even runs.
    Task task = service.getTaskById(taskId);
    model.addAttribute("task", task);
    return "tasks/view";
}
```

JSP side:

```jsp
<c:if test="${not empty message}">
    <div class="alert alert-success">${message}</div>
</c:if>
```

Shows once right after creation, disappears on refresh. Exactly the PRG pattern I want.

---

## Gotcha to remember

Flash attributes rely on `HttpSession` under the hood by default (`SessionFlashMapManager`).
Since I'm not using Spring Boot and I'm wiring everything manually in a Dynamic Web Project —
**sessions have to actually be working** in my setup, or `addFlashAttribute()` will silently
fail to carry the data across. If a flash message ever mysteriously doesn't show up, check
session config first.

---

## One-line takeaway to remember forever

> `addAttribute` = "show it in the URL, keep it after refresh."
> `addFlashAttribute` = "hide it from the URL, use it once, then forget it."
