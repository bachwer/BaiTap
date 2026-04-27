# Personal Todo App (Mini Project 1)

## Features implemented

- CRUD todo with one table (`Todo`): `id`, `content`, `dueDate`, `status`, `priority`.
- Bean Validation:
  - `@NotBlank` for `content`
  - `@FutureOrPresent` for `dueDate`
- Thymeleaf list + form in one page.
- Edit flow using `@PathVariable` and reusing the same form.
- Delete flow with JavaScript confirmation.
- Session owner flow:
  - Owner input page at `/owner`
  - Save owner to session via `session.setAttribute("ownerName", name)`
  - Guard `/todos` to redirect to `/owner` if owner is missing
- I18N (VI/EN): `messages_vi.properties`, `messages_en.properties`, `LocaleResolver`, `LocaleChangeInterceptor` with `?lang=`.

## Main routes

- `GET /` -> owner input page (or redirect to `/todos` when owner exists)
- `POST /owner` -> save owner to session
- `GET /todos` -> todo list + form
- `GET /todos/{id}/edit` -> load edit mode
- `POST /todos/save` -> create/update
- `POST /todos/{id}/delete` -> delete
- `POST /owner/clear` -> clear owner session

## Run

```bash
cd "/Users/macbook/Downloads/BaiTap_27_04"
./gradlew bootRun
```

Open: `http://localhost:8080`

## Test

```bash
cd "/Users/macbook/Downloads/BaiTap_27_04"
./gradlew test
```

`src/test/resources/application.properties` uses H2 in-memory database for tests.

