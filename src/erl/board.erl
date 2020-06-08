%%%-------------------------------------------------------------------
%%% @author anian
%%% @copyright (C) 2020, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 02. Jun 2020 11:18 PM
%%%-------------------------------------------------------------------
-module(board).

%% API
-export([createGame/0, isValidPosition/1, addNeighboursOf/2, takeTurn/2, getTurn/1]).

-record(game, {xs, os, grays, turn, hasEnded}).

createGame() -> #game{xs = [], os = [], grays = [], turn = "O", hasEnded = false}.

isValidPosition({X, Y}) -> (X < 6) and (X > -1) and (Y < 6) and (Y > -1).

isFreeAt(Position, Game) ->
  IsTaken = (lists:member(Position, Game#game.xs) or lists:member(Position, Game#game.os)
    or lists:member(Position, Game#game.grays)),
  if
    not IsTaken -> true;
    true -> false
  end.



addNeighbours([], Game) -> Game;
addNeighbours([P | T], Game) ->
  IsValidFree = isValidPosition(P) and isFreeAt(P, Game),
  if
    IsValidFree -> addNeighbours(T, #game{xs = Game#game.xs, os = Game#game.os, grays = [P | Game#game.grays], turn = Game#game.turn, hasEnded = false});
    true -> addNeighbours(T, Game)
  end.


addNeighboursOf({X, Y}, Game) ->
  NewGrays = [{X+1, Y}, {X+1, Y+1}, {X+1, Y-1}, {X-1, Y}, {X-1, Y+1}, {X-1, Y-1}, {X, Y+1}, {X, Y-1}],
  NewGame = addNeighbours(NewGrays, Game),
  NewGame#game.grays.


isMoveAvailable(Game) ->
  lists:flatlength(Game#game.grays)
    + lists:flatlength(Game#game.xs)
    + lists:flatlength(Game#game.os) < 36.

takeTurn(Position, Game) ->
  IsValidFree = isValidPosition(Position) and isFreeAt(Position, Game),

  if
    IsValidFree ->
      case Game#game.turn of
        "X" -> NewGame = #game{xs = [Position | Game#game.xs],
          os = Game#game.os,
          grays = addNeighboursOf(Position, Game),
          turn = "O",
          hasEnded = false},
          #game{xs = NewGame#game.xs,
            os = NewGame#game.os,
            grays = NewGame#game.grays,
            turn = NewGame#game.turn,
            hasEnded = not isMoveAvailable(NewGame)};
        "O" -> NewGame = #game{xs = Game#game.xs,
          os = [Position | Game#game.os],
          grays = addNeighboursOf(Position, Game),
          turn = "X",
          hasEnded = false},
          #game{xs = NewGame#game.xs,
            os = NewGame#game.os,
            grays = NewGame#game.grays,
            turn = NewGame#game.turn,
            hasEnded = not isMoveAvailable(NewGame)};
          _   -> NewGame = Game
      end;
    true -> error
  end.




getTurn(Game) -> Game#game.turn.



