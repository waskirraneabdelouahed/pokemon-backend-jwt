# pokemon-backend-jwt
adding spring security (JWT) to secure pokemons apis 

this is a simple example of making your apis private and separatin their access by Roles.
using JWT as the authorization method to check for users auth.
there are two roles: ADMIN and USER.

 * USER has limited access: he canonly access to the public apis (e.g. "localhost:8000/", "localhost:8000/api/auth/**" ... etc) and to the apis ("localhost:8000/pokemon/view" wich has limited view of the info on the pokemons

 * ADMIN has access to every api in the app.
 
How does it work:

- every user has to signup with a unique email and username and also a password.
-we then persist the user in our DB.
-the first time the user signs in. he will get a response body containing the token and its type on top of his authoritie(his role/s).
-the subsequent request that contain the token will get access directly to the apis according to the user's role (if the token is legit and not expired of course)
