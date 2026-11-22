# JSONPlaceholder API Test Cases

Base URL: `https://jsonplaceholder.typicode.com`. Writes are simulated by JSONPlaceholder and are not expected to persist; each write case validates only its response.

| ID | Endpoint | Method | Expected status | Key assertions | Automated test name |
|---|---|---|---:|---|---|
| TC-API-001 | `/posts` | GET | 200 | JSON array contains 100 posts. | `PostsApiTest.getAllPostsReturnsOneHundredPosts` |
| TC-API-002 | `/posts/1` | GET | 200 | `id` is 1; `userId`, `title`, and `body` are present. | `PostsApiTest.getPostByIdReturnsRequiredFields` |
| TC-API-003 | `/posts/1` | GET | 200 | `Content-Type` is JSON and response time is under 2,000 ms. | `PostsApiTest.getPostResponseIsJsonAndWithinTwoSeconds` |
| TC-API-004 | `/posts/9999` | GET | 404 | Unknown post returns not found. | `PostsApiTest.getUnknownPostReturnsNotFound` |
| TC-API-005 | `/posts?userId=1` | GET | 200 | Result is non-empty and every post has `userId` 1. | `PostsApiTest.getPostsByUserReturnsOnlyMatchingPosts` |
| TC-API-006 | `/users/1` | GET | 200 | User email matches a valid email format. | `UsersApiTest.getUserByIdReturnsValidEmail` |
| TC-API-007 | `/posts/1/comments` | GET | 200 | Result is non-empty and every comment has `postId` 1. | `CommentsApiTest.getCommentsForPostReturnsMatchingPostIds` |
| TC-API-008 | `/posts` | POST | 201 | Response echoes submitted title, body, and userId and includes an id. No later GET is used to imply persistence. | `PostsApiTest.createPostReturnsCreatedPostWithoutAssumingPersistence` |
| TC-API-009 | `/posts/1` | PUT | 200 | Response contains the updated title. No later GET is used to imply persistence. | `PostsApiTest.updatePostReturnsUpdatedTitleWithoutAssumingPersistence` |
| TC-API-010 | `/posts/1` | DELETE | 200 | Delete request returns the observed successful status. No later GET is used to imply persistence. | `PostsApiTest.deletePostReturnsSuccessWithoutAssumingPersistence` |
