%%%-------------------------------------------------------------------
%%% @author anian
%%% @copyright (C) 2020, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 06. Jun 2020 10:52 PM
%%%-------------------------------------------------------------------
-module(board_gen_server).
-behavior(gen_server).

%% API
-export([start_link/0, init/1, handle_cast/2, handle_call/3, handle_info/2, code_change/3, terminate/2]).
-export([start/0, stop/0, takeTurn/1, getGame/0, newGame/0]).

start_link() -> gen_server:start_link({local, ?MODULE}, ?MODULE, [], []).
init([])     -> {ok, board:createGame()}.

%% USER INTERFACE

start() -> start_link().

stop()  -> gen_server:cast(?MODULE, stop).

takeTurn(Position) ->
  gen_server:call(?MODULE, {turn, Position}).

getGame() ->
  gen_server:call(?MODULE, {get}).

newGame() ->
  gen_server:call(?MODULE, {new}).



%% CALLBACKS

handle_cast(stop, Game) ->
  {stop, normal, Game}.


handle_call({turn, Position}, _From, Game) ->
  UpdatedGame = board:takeTurn(Position, Game),
  case UpdatedGame of
    error -> {reply, Game, Game};
    _     -> {reply, UpdatedGame, UpdatedGame}
  end;

handle_call({get}, _From, Game) ->
  {reply, Game, Game};

handle_call({new}, _From, _) ->
  {reply, board:createGame(), board:createGame()}.


handle_info(_Info, State) -> {noreply, State}.

code_change(_OldVsn, State, _Extra) -> {ok, State}.


terminate(Reason, _) ->
  Reason.