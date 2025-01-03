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
```json
{
  "id": "com.google.common.eventbus.Dispatcher",
  "javadocData": [
    {
      "id": 1,
      "javadoc": "{\"description\":{\"elements\":[\"Handler for dispatching events to subscribers, providing different event ordering guarantees that\\nmake sense for different situations.\\n\\n<p><b>Note:</b> The dispatcher is orthogonal to the subscriber's \",{\"type\":\"CODE\",\"content\":\" Executor\",\"name\":\"code\"},\". The dispatcher\\ncontrols the order in which events are dispatched, while the executor controls how (i.e. on which\\nthread) the subscriber is actually called when an event is dispatched to it.\"],\"empty\":false},\"blockTags\":[{\"type\":\"AUTHOR\",\"content\":{\"elements\":[\"Colin Decker\"],\"empty\":false},\"name\":null,\"tagName\":\"author\"}]}"
    },
    {
      "id": 2,
      "javadoc": "{\"description\":{\"elements\":[\"Returns a dispatcher that queues events that are posted reentrantly on a thread that is already\\ndispatching an event, guaranteeing that all events posted on a single thread are dispatched to\\nall subscribers in the order they are posted.\\n\\n<p>When all subscribers are dispatched to using a <i>direct</i> executor (which dispatches on\\nthe same thread that posts the event), this yields a breadth-first dispatch order on each\\nthread. That is, all subscribers to a single event A will be called before any subscribers to\\nany events B and C that are posted to the event bus by the subscribers to A.\"],\"empty\":false},\"blockTags\":[]}"
    },
    {
      "id": 3,
      "javadoc": "{\"description\":{\"elements\":[\"Returns a dispatcher that queues events that are posted in a single global queue. This behavior\\nmatches the original behavior of AsyncEventBus exactly, but is otherwise not especially useful.\\nFor async dispatch, an \",{\"type\":\"LINKPLAIN\",\"content\":\" #immediate() immediate\",\"name\":\"linkplain\"},\" dispatcher should generally be\\npreferable.\"],\"empty\":false},\"blockTags\":[]}"
    },
    {
      "id": 4,
      "javadoc": "{\"description\":{\"elements\":[\"Returns a dispatcher that dispatches events to subscribers immediately as they're posted\\nwithout using an intermediate queue to change the dispatch order. This is effectively a\\ndepth-first dispatch order, vs. breadth-first when using a queue.\"],\"empty\":false},\"blockTags\":[]}"
    },
    {
      "id": 5,
      "javadoc": "{\"description\":{\"elements\":[\"Dispatches the given \",{\"type\":\"CODE\",\"content\":\" event\",\"name\":\"code\"},\" to the given \",{\"type\":\"CODE\",\"content\":\" subscribers\",\"name\":\"code\"},\".\"],\"empty\":false},\"blockTags\":[]}"
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