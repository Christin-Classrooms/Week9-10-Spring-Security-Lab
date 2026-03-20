# Lab 5 & 6 - Spring Security (Authentication & Role-Based Authorization)

## Course Information

* **Course:** CPAN 228
* **Topic:** Spring Security — Authentication, Authorization & SecurityFilterChain

---

## Overview

In Lab 4 you built custom queries and pagination on top of the existing Fighter/Player application. In this lab you will secure that same application using **Spring Security**.

The project already has Fighter and Player pages built. However, you will need to create a simple login page and adapt the existing player creation logic into a registration flow. Your job is to wire up the security layer: add the dependency, configure the filter chain, implement user loading, protect the right routes, and make sure players cannot access admin pages.

By the end of this lab, unauthenticated users will be redirected to login, Players will only be able to reach their own pages, and Admins will have full access to Fighter and Player management — including the H2 console.

---

## Domain Model

| Role | Who they are | What they can access |
|------|-------------|----------------------|
| `PLAYER` | Self-registered users | Dashboard only |
| `ADMIN` | Seeded on startup | Everything — fighters, players, H2 console |

---

## Getting Started

### GitHub Setup

1. **Accept the GitHub Classroom Assignment**
   - Click the assignment link provided on Blackboard

2. **Clone Your Repository**
   ```bash
   git clone https://github.com/YOUR-USERNAME/YOUR-REPO-NAME.git
   cd YOUR-REPO-NAME
   ```

3. **Create a Feature Branch**
   ```bash
   git checkout -b feature/lab5-yourname
   ```
   Replace `yourname` with your actual name (e.g., `feature/lab5-jane-doe`)

4. **Run the Starter Project**
   ```bash
   mvn spring-boot:run
   ```
   Confirm it starts and the existing Fighter/Player pages are reachable before adding any security.

---

## Lab 5 Assignment

### Part 1 — Add the Spring Security Dependency

The starter project does **not** have Spring Security. Your first task is to add it.

Open `pom.xml` and add **two** dependencies inside the `<dependencies>` block:

- `spring-boot-starter-security` from the `org.springframework.boot` group
- `thymeleaf-extras-springsecurity6` from the `org.thymeleaf.extras` group

> **What happens the moment you add Spring Security?** Try running the app after adding only the dependency — before writing any configuration. Notice what happens when you try to access any page. This is Spring Security's default behaviour and it's important to understand why.

---

### Part 2 — Update the `Player` Entity

The `Player` entity will serve as the application's user. It needs two new fields to support authentication:

- A `password` field to store the BCrypt-encoded password
- A `role` field to store the user's role (e.g. `"PLAYER"` or `"ADMIN"`)

Add both fields to your `Player` entity. Make sure they have the appropriate getters and setters (Lombok can help here).

> **Note:** You do not need to change your database schema manually. Since the project uses H2 with `ddl-auto`, the table will update automatically on the next startup.

---

### Part 3 — Implement `PlayerDetailsService`

Spring Security has no idea your `Player` table exists. You need to create a service that bridges the two.

Create a new class called `PlayerDetailsService` in your `service` package. It should:

- Implement the `UserDetailsService` interface from Spring Security
- Be annotated with `@Service`
- `@Autowired` your `PlayerRepository`
- Override the `loadUserByUsername` method to look up a `Player` by username from the database
- Return a Spring Security `UserDetails` object built from the `Player`'s username, password, and role

> **Hint:** Use the `User` builder from `org.springframework.security.core.userdetails`. Call `.roles(player.getRole())` — Spring Security will automatically prepend `ROLE_` for you.
>
> If no player is found, throw a `UsernameNotFoundException`.

---

### Part 4 — Create the `SecurityConfig`

Create a new class called `SecurityConfig` in a `config` package (create the package if it doesn't exist). You can use the `SecurityConfig` we built together in class as your reference.

Your config class should:

- Be annotated with `@Configuration` and `@EnableWebSecurity`
- Define a `PasswordEncoder` bean that uses `BCryptPasswordEncoder`
- Define a `SecurityFilterChain` bean that configures the following rules:

#### Route Rules (in this order):

| URL Pattern | Who can access it |
|-------------|------------------|
| `/login`, `/register` | Everyone (no login required) |
| `/create-fighter` | `ADMIN` only |
| `/h2-console/**` | `ADMIN` only |
| Everything else (including `/`, `/fighters/`, and `/players`) | Any authenticated user |

#### Additional requirements for the `SecurityFilterChain`:

- Configure **form login** to use `/login` as the login page and redirect to `/` on success
- Configure **logout** to redirect to `/login?logout` on success
- The H2 console uses `<iframe>` tags internally, which Spring Security blocks by default. You will need to **disable the `X-Frame-Options` header** and **disable CSRF** for the H2 console path to work for admins. Look at how we handled this in the in-class project.

> **Reminder:** Rule order matters — Spring Security evaluates rules top to bottom and stops at the first match. Always put specific rules before the catch-all `.anyRequest()`.

---

### Part 5 — Create the Login Page

The project does not include a login page. You will need to create one to support Spring Security's form login.

In your `src/main/resources/templates` folder (and in a controller to serve the view):

- Create a new file called `login.html`
- Build a simple HTML form that `POST`s to `/login`
- Include two input fields: one named `username` and one named `password`
- Add a submit button
- Add a controller method to serve the `login` view (e.g., `GET /login`)

> **Note:** Spring Security handles the `POST /login` route automatically. You do not need to write a controller method for the POST request, only a GET method to show the page.
>
> 💡 **Example Code:** You can refer to the class example code for guidance at: [https://github.com/Christin-Classrooms/Week9-10-Spring-Security](https://github.com/Christin-Classrooms/Week9-10-Spring-Security)

---

### Part 6 — Refactor "Create Player" to "Register"

The project already has a way to create a Player. Instead of building a new controller from scratch, you will **refactor** the existing create-player logic to serve as a proper registration flow.

In your `PlayerController` (or wherever the player creation currently lives):

- Change the method names and routes to clearly represent registration (e.g., `GET /register` and `POST /register`)
- Rename the existing `create-player.html` template (if applicable) to `register.html`, or ensure the existing form action points to `/register`
- Make sure the password is **encoded with BCrypt** before saving — never store plain text
- Assign the role `"PLAYER"` to every self-registered user
- After a successful registration, redirect the user to `/login`

---

### Part 7 — Seed an Admin User with `DataLoader`

There is no UI to create an admin, and your `/register` endpoint always assigns the `PLAYER` role. You need a way to create an admin user on startup.

Create a `DataLoader` class and use the code we built in class as your reference. It should:

- Be annotated with `@Component` and implement `ApplicationRunner`
- `@Autowired` your `PlayerRepository` and `PasswordEncoder`
- On startup, check if a player with the username `"admin"` already exists
- If not, create one with the password `"admin123"` (BCrypt-encoded) and role `"ADMIN"` and save it

> **Why check first?** Without the `if` check, a new admin would be inserted every time the app restarts — or worse, throw a duplicate key error.

---

### Part 8 — Protect the Navigation Links in Thymeleaf

The `SecurityFilterChain` prevents unauthorized access on the server side. But players should also not even *see* the admin links in the navigation. Use the **Thymeleaf Spring Security extras** to conditionally show links based on role.

In your layout or navigation template(s):

- Add the Spring Security Thymeleaf namespace to the `<html>` tag:  
  `xmlns:sec="http://www.thymeleaf.org/extras/spring-security"`
- Use `sec:authorize="hasRole('ADMIN')"` to wrap any links to building/creating a fighter, or the H2 console — so only admins see them
- Add a logout button visible to all authenticated users using `sec:authorize="isAuthenticated()"`

> **Important distinction:** `sec:authorize` only hides elements in the HTML. It does *not* protect the routes. Your `SecurityFilterChain` is what actually blocks unauthorized requests. You need both.

---

## Testing Your Work

After completing all parts, verify each of the following scenarios manually:

### As an unauthenticated user
- [ ] Visit `/` → should redirect to `/login`
- [ ] Visit `/fighters/` → should redirect to `/login`
- [ ] Visit `/login` → should load the login page
- [ ] Visit `/register` → should load the registration form

### As a self-registered Player
- [ ] Register a new account → should redirect to `/login`
- [ ] Log in → should land on `/`
- [ ] Navigate to `/fighters/` → should have access to view the list of fighters
- [ ] Navigate to `/players` → should have access to view the list of players
- [ ] Try to navigate to `/create-fighter` → should be denied (403 or redirect)
- [ ] "Create Fighter" and H2 Console links should **not** be visible in the nav
- [ ] A logout button should be visible and work correctly

### As the Admin (`admin` / `admin123`)
- [ ] Log in → should land on `/`
- [ ] Navigate to `/fighters/` → should have access to view the list of fighters
- [ ] Navigate to `/create-fighter` → should have access to create a new fighter
- [ ] Navigate to `/players` → should have access to view the list of players
- [ ] Navigate to `/h2-console` → should load correctly (no framing errors)
- [ ] "Create Fighter" and H2 Console links **should** be visible in the nav

---

## Common Issues

| Symptom | Likely cause | Where to look |
|---------|-------------|---------------|
| Redirect loop on `/login` | `/login` not in `permitAll()` | Part 4 — check your route rules |
| Login always fails | Password not encoded on save | Part 6 — check your register method |
| Admin can't open H2 console | CSRF / frame headers still active | Part 4 — H2 console config |
| `sec:authorize` not working | Missing Thymeleaf Security namespace | Part 8 — check your `<html>` tag |
| All users get 403 after login | Role stored as `ROLE_PLAYER` but using `hasRole("ROLE_PLAYER")` | Part 4 — `hasRole()` adds `ROLE_` automatically, don't double-prefix |
| Admin seed fails on restart | No duplicate check before saving | Part 7 — add the `if` guard |

---

## Development Workflow

```bash
# Run the app
mvn spring-boot:run

# Commit your work
git add .
git commit -m "Lab 5: Implement Spring Security with roles and SecurityFilterChain"

# Push to your branch
git push origin feature/lab5-yourname
```

Then open a Pull Request and submit the link on Blackboard.

---

## Resources

* [Class--Code](https://github.com/Christin-Classrooms/Week9-10-Spring-Security)
* [Custom Queries Cheatsheet](CustomQueries_CHEATSHEET.md)
* [Thymeleaf Cheat Sheet](THYMELEAF_CHEATSHEET.md)
* [Spring Security Docs](https://spring.io/projects/spring-security)
* [Spring Boot Reference — Security](https://docs.spring.io/spring-boot/docs/current/reference/html/web.html#web.security)
