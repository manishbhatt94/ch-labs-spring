# Study Notes: BeanPostProcessor Ordering & "ObjectProvider"

Two things that look like they should follow the same rules as everything else in
Spring... and quietly don't.

---

## 1. Ordering "BeanPostProcessor"s

### The soul of it

`BeanPostProcessor`s are container **plumbing** — they have to be discovered, sorted,
and switched on *before* almost anything else can happen during startup. Because of
that bootstrap-critical timing, Spring never trusted the general-purpose "sort by
`@Order`" machinery for this one job. It hardcoded a cruder, faster check instead:
**does this class literally `implement Ordered`?** — a plain `instanceof` test, not an
annotation scan.

That one design decision is the root of everything below.

### What actually happens at startup

Spring finds every `BeanPostProcessor` bean and sorts it into exactly one of three
buckets, using `instanceof`-style checks — **not** annotation scanning:

```txt
1. implements PriorityOrdered  -> priorityOrderedPostProcessors
2. implements Ordered          -> orderedPostProcessors
3. neither                     -> nonOrderedPostProcessors   (@Order here does NOTHING)
```

Each bucket is registered as its own **sequential pass** — bucket 1 entirely, then
bucket 2 entirely, then bucket 3 entirely. Within buckets 1 and 2, beans are sorted by
`getOrder()` (lower number = higher priority, same convention as everywhere else in
Spring). Bucket 3 just keeps whatever order the beans were found in (typically scan
order) — `@Order` is silently ignored here because the bean never even reached the
sorting step; it was excluded from both ordered buckets before sorting was considered.

### The takeaway

> For `BeanPostProcessor`s specifically: **`implements Ordered`, not `@Order`.**
> This is the one corner of Spring where the annotation and the interface are *not*
> interchangeable, because BPP registration bypasses the usual annotation-aware
> sorting logic entirely.

```java
@Component
public class MyBpp implements BeanPostProcessor, Ordered {
    @Override
    public int getOrder() { return 100; } // lower = runs earlier
    ...
}
```

Also worth remembering: `PriorityOrdered` isn't just "extra important `Ordered`" — it's
a **whole separate, earlier pass**. A `PriorityOrdered` BPP with order `500` still runs
before *any* plain `Ordered` BPP with order `1`, because the passes themselves are
sequential, not merged and sorted together.

---

## 2. "ObjectProvider\<T\>" and "getBeanProvider(Class\<T\>)"

### The soul of it

`getBean(Class<T>)` is blunt: zero matches → exception, more than one match with no
`@Primary` → exception. Fine for "I need exactly one thing, right now." Not fine for
"maybe zero, maybe many, and I'll decide what to do about it" — that's a different
*shape* of problem, and Spring gives it a different tool.

`ObjectProvider<T>` is that tool: a small handle to a bean *type* (not a bean itself)
that lets you ask softer questions — optional lookups, multi-bean lookups, and
**ordered** multi-bean lookups — without ever risking an unhandled exception from a
simple `getBean` call.

Get one the same way you'd get any bean, just asking for a provider instead of the bean directly:

```java
ObjectProvider<Validator> provider = ctx.getBeanProvider(Validator.class);
```

### What it can do

| Method | Answers... |
|---|---|
| `getObject()` | "Give me the one bean" — same failure modes as `getBean(Class)` |
| `getIfAvailable()` | "Give me the one bean, or `null`/a default if there isn't one" — no exception |
| `getIfUnique()` | "Give me the one bean, or `null` if it's ambiguous" — no exception |
| `stream()` | "Give me *all* of them" — unordered (registration order) |
| `orderedStream()` | "Give me *all* of them, **sorted**" |

### Why "orderedStream()" is the one that matters most here

This is the **manual, programmatic version** of what `@Autowired List<T>` /
`@Autowired T[]` does automatically at an injection point. Same sorting rule, same
comparator, just triggered by a method call instead of a field declaration:

```java
ctx.getBeanProvider(Validator.class)
   .orderedStream()
   .forEach(v -> ...);   // sorted by @Order / Ordered / @Priority
```

### The important contrast with part 1

This is the twist that makes both notes worth keeping side by side:

> `orderedStream()`'s comparator honors **both** `Ordered` **and** `@Order`/`@Priority`.
> `BeanPostProcessor` registration honors **only** `Ordered`.

Same-looking annotation, same intent, two completely different outcomes — purely
because they're implemented by two different pieces of Spring internals, one general
(`AnnotationAwareOrderComparator`, used almost everywhere — collections, `orderedStream()`,
`@Configuration` class ordering, etc.) and one special-cased for early bootstrap
(`PostProcessorRegistrationDelegate`, `instanceof`-only). When in doubt about whether
`@Order` will "just work" somewhere in Spring, the honest answer is: *usually yes,
except in the one or two places where startup timing forced Spring to cut that corner.*
`BeanPostProcessor` registration is the main one worth remembering by name.

---

## One-line summary of both

- **BPP ordering:** `implements Ordered`, always — `@Order` is a no-op here.
- **`ObjectProvider`:** the programmatic escape hatch for optional/multi/ordered bean
  lookups, standing in for what `@Autowired` would otherwise do at an injection point.
