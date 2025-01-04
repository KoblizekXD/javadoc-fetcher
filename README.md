# Javadoc Fetcher
This is a backend api that fetches javadoc in a JSON format from 
a given artifact at a specific repository.

## Example

For a `POST` request to the following route: `/api/javadoc`
with following body:
```json
{
  "repository": "https://repo1.maven.org/maven2",
  "groupId": "com.google.guava",
  "artifactId": "guava",
  "version": "33.4.0-jre",
  "classData": "com.google.common.eventbus.Dispatcher"
}
```

The response would be following:
```json lines
{
  "id": "com.google.common.eventbus.Dispatcher",
  "javadocData": [
    {
      "id": 1,
      "javadoc": {
        "description": {
          "elements": [
            "Handler for dispatching events to subscribers, providing different event ordering guarantees that\nmake sense for different situations.\n\n<p><b>Note:</b> The dispatcher is orthogonal to the subscriber's ",
            {
              "type": "CODE",
              "content": " Executor",
              "name": "code"
            },
            ". The dispatcher\ncontrols the order in which events are dispatched, while the executor controls how (i.e. on which\nthread) the subscriber is actually called when an event is dispatched to it."
          ],
          "empty": false
        },
        "blockTags": [
          {
            "type": "AUTHOR",
            "content": {
              "elements": [
                "Colin Decker"
              ],
              "empty": false
            },
            "name": null,
            "tagName": "author"
          }
        ]
      },
      "attachedType": "CLASS",
      "attachedName": "com.google.common.eventbus.Dispatcher"
    },
    {
      "id": 2,
      "javadoc": {
        // ...
      },
      "attachedType": "METHOD",
      "attachedName": "perThreadDispatchQueue"
    },
    {
      "id": 3,
      "javadoc": {
        // ...
      },
      "attachedType": "METHOD",
      "attachedName": "legacyAsync"
    },
    {
      "id": 4,
      "javadoc": {
        // ...
      },
      "attachedType": "METHOD",
      "attachedName": "immediate"
    },
    {
      "id": 5,
      "javadoc": {
        // ...
      },
      "attachedType": "METHOD",
      "attachedName": "dispatch"
    }
  ]
}
```
This may come very useful when you want to fetch javadoc for a specific class in a specific artifact.
Javadocs are cached, so it takes a lot of time only the first time you fetch the javadoc for the specific class.

### Routes
The backend currently exposes 2 routes:
1. `/api/javadoc` - This route is used to fetch javadoc for a specific class in a specific artifact.
    Here's an example of the request body:
    ```json
    {
      "repository": "https://repo1.maven.org/maven2",
      "groupId": "com.google.guava",
      "artifactId": "guava",
      "version": "33.4.0-jre",
      "classData": "com.google.common.eventbus.Dispatcher"
    }
    ```
2. `/api/javadoc/classes` - This route is used to fetch all the classes in a specific artifact.
    All the classes can be used inside of `classData` in the `/api/javadoc` route.
    Here's an example of the request body:
    ```json
    {
      "repository": "https://repo1.maven.org/maven2",
      "groupId": "com.google.guava",
      "artifactId": "guava",
      "version": "33.4.0-jre"
    }
    ```
   
## Running

You will need a Java 23 environment and Postgres database in order
to run this project.
You will also need to set following environment variables:
- `JDBC_DATABASE_URL` - The url of the database
- `JDBC_DATABASE_USERNAME` - The username of the database
- `JDBC_DATABASE_PASSWORD` - The password of the database

## Licensing

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.