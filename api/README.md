# Setup
Create a MySQL schema with the name "recall". Add environment variables to connect to your database:

```dotenv
" Add your credentials
MYSQL_DB="recall"
MYSQL_USERNAME=""
MYSQL_PASSWORD=""
```

# API
- GET `/api/v1/deck` to get all published decks.
- POST `/api/v1/deck` to publish a new deck. Example:
```json
{
  "title": "Test 2",
  "icon": "😃",
  "color": "#FFFFFF",
  "cards": [
    {
      "front": "Front",
      "back": "Back"
    },
    {
      "front": "Front 2",
      "back": "Back 2"
    }
  ]
}
```
- GET `/api/v1/deck/:id` to get published deck with id.
