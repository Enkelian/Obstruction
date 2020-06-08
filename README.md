# Obstruction
A simple example of Jinterface usage. 

Run this project in IntelliJ with JDK11. You'll also need Erlang.

To run the game, create a named Erlang node:
`erl -sname eNode -setcookie erljava`

Then compile Erlang modules:

`c(board).`

`c(board_gen_server)`

Now start the server:

`board_gen_server:start()`

And run the client in the main function of the Java application (in Client class).

Video (in Polish):
<https://www.youtube.com/watch?v=tETrrV2t-u8>

