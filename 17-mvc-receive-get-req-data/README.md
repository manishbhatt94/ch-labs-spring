# TasksApp

A learning project for practicing **Spring MVC 5.3 (vanilla, no Spring Boot, no build tools)** on a Dynamic Web Project with Servlet 3.1 and Java 8.

The core goal was to explore the **different ways a Spring MVC controller can accept client data**, by deliberately using a different approach in different endpoints:
- Raw `HttpServletRequest` (manually reading parameters)
- `@RequestParam` (single named parameters, required and optional)
- `@PathVariable` (values embedded in the URL path)
- `@ModelAttribute` (binding whole form submissions into a POJO)

Along the way, this project also covers:
- JSP views with JSTL.
- The `"redirect:"` special return string for post-action redirects (e.g. after creating a resource).
    - This is the **PRG (Post-Redirect-Get) Pattern** which saves us from the Form data re-submission
     problem on refreshing page after submitting data in case if we directly render a view (return
     a view logical name) after a POST instead of doing a re-direct.
- In-memory data handling via a service layer (no database).


Data is stored in `TasksAppDataService`, in `ArrayList`s, across 4 models: `Department`, `User`, `Project`, `Task`.


> It is sometimes desirable to issue an HTTP redirect back to the client, before the view is rendered.
> This is desirable, for example, when one controller has been called with POST data, and the response
> is actually a delegation to another controller (for example on a successful form submission). In this
> case, a normal internal forward will mean that the other controller will also see the same POST data,
> which is potentially problematic if it can confuse it with other expected data. Another reason to
> perform a redirect before displaying the result is to eliminate the possibility of the user submitting
> the form data multiple times. In this scenario, the browser will first send an initial POST; it will
> then receive a response to redirect to a different URL; and finally the browser will perform a
> subsequent GET for the URL named in the redirect response. Thus, from the perspective of the browser,
> the current page does not reflect the result of a POST but rather of a GET. The end effect is that
> there is no way the user can accidentally re- POST the same data by performing a refresh. The refresh
> forces a GET of the result page, not a resend of the initial POST data.
> — [Spring 4.3.x MVC - &sect; 22.5.3 Redirecting to Views](https://docs.spring.io/spring-framework/docs/4.3.x/spring-framework-reference/html/mvc.html#mvc-redirecting)


## Demo Screenshots

Project Demo Screenshots are attached in file: [DEMO.md](./DEMO.md)


## Endpoints

| Method | Path | Data-binding approach used | Description |
|---|---|---|---|
| GET | `/tasks-app/departments` | — | List all departments |
| POST | `/tasks-app/departments` | `HttpServletRequest` | Create a new department |
| GET | `/tasks-app/filter-departments` | `HttpServletRequest` | Filter departments by name |
| GET | `/tasks-app/departments/{deptId}` | `@PathVariable` | View a single department |
| GET | `/tasks-app/users` | — | List all users |
| GET | `/tasks-app/create-user` | — | Show user creation form |
| POST | `/tasks-app/users` | `@RequestParam` | Create a new user |
| GET | `/tasks-app/filter-users` | `@RequestParam` | Filter users by name and/or department |
| GET | `/tasks-app/users/{userId}` | `@PathVariable` | View a single user |
| GET | `/tasks-app/projects` | — | List all projects |
| POST | `/tasks-app/projects` | `@ModelAttribute` | Create a new project |
| GET | `/tasks-app/filter-projects` | `@RequestParam` | Filter projects by name |
| GET | `/tasks-app/projects/{projId}` | `@PathVariable` | View a single project |
| GET | `/tasks-app/tasks` | — | List all tasks |
| GET | `/tasks-app/create-task` | — | Show task creation form |
| POST | `/tasks-app/tasks` | `@ModelAttribute` | Create a new task |
| GET | `/tasks-app/filter-tasks` | `@ModelAttribute` | Filter tasks by name, project, and/or assignee |
| GET | `/tasks-app/tasks/{taskId}` | `@PathVariable` | View a single task |

