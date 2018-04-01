cah.longpoll.ErrorCodeHandlers[cah.$.ErrorCode.NOT_REGISTERED]=function(data){cah.longpoll.Resume=false;cah.log.error("The server seems to have restarted. Any in-progress games have been lost.");cah.log.error("You will need to refresh the page to start a new game.");$("input").attr("disabled","disabled");};cah.longpoll.EventHandlers[cah.$.LongPollEvent.NEW_PLAYER]=function(data){if(data[cah.$.LongPollResponse.NICKNAME]!=cah.nickname&&!cah.hideConnectQuit){cah.log.status(data[cah.$.LongPollResponse.NICKNAME]+" has connected.");}};cah.longpoll.EventHandlers[cah.$.LongPollEvent.PLAYER_LEAVE]=function(data){var friendly_reason="Leaving";var show=!cah.hideConnectQuit;switch(data[cah.$.LongPollResponse.REASON]){case cah.$.DisconnectReason.BANNED:friendly_reason="Banned";show=true;break;case cah.$.DisconnectReason.IDLE_TIMEOUT:friendly_reason="Kicked due to idle";break;case cah.$.DisconnectReason.KICKED:friendly_reason="Kicked by server administrator";show=true;break;case cah.$.DisconnectReason.MANUAL:friendly_reason="Leaving";break;case cah.$.DisconnectReason.PING_TIMEOUT:friendly_reason="Ping timeout";break;}
if(show){cah.log.status(data[cah.$.LongPollResponse.NICKNAME]+" has disconnected ("+ friendly_reason
+").");}};cah.longpoll.EventHandlers[cah.$.LongPollEvent.NOOP]=function(data){};cah.longpoll.EventHandlers[cah.$.LongPollEvent.KICKED]=function(){cah.log.status("You have been kicked by the server administrator.");cah.longpoll.Resume=false;$("input").attr("disabled","disabled");$("#menubar_left").empty();$("#main").empty();$("#info_area").empty();};cah.longpoll.EventHandlers[cah.$.LongPollEvent.BANNED]=function(){cah.log.status("You have been banned by the server administrator.");cah.longpoll.Resume=false;$("input").attr("disabled","disabled");$("#menubar_left").empty();$("#main").empty();$("#info_area").empty();};cah.longpoll.EventHandlers[cah.$.LongPollEvent.CHAT]=function(data){var clazz=undefined;var from=data[cah.$.LongPollResponse.FROM];var show=!cah.ignoreList[from];var game=null;if(data[cah.$.LongPollResponse.FROM_ADMIN]){clazz="admin";show=true;}
if(data[cah.$.LongPollResponse.WALL]){cah.log.everyWindow("Global message from "+ from+": "+ data[cah.$.LongPollResponse.MESSAGE],clazz);}else{if(cah.$.LongPollResponse.GAME_ID in data){game=data[cah.$.LongPollResponse.GAME_ID];}
if(from!=cah.nickname&&show){var message=data[cah.$.LongPollResponse.MESSAGE];if(data[cah.$.LongPollResponse.EMOTE]){cah.log.status_with_game(game,"* "+ from+" "+ message,clazz);}else{cah.log.status_with_game(game,"<"+ from+"> "+ message,clazz);}}}};cah.longpoll.EventHandlers[cah.$.LongPollEvent.GAME_LIST_REFRESH]=function(data){cah.GameList.instance.update();};cah.longpoll.EventHandlers[cah.$.LongPollEvent.GAME_PLAYER_JOIN]=function(data){cah.longpoll.EventHandlers.__gameEvent(data,cah.Game.prototype.playerJoin,data[cah.$.LongPollResponse.NICKNAME],"player join (if you just joined a game this may be OK)");};cah.longpoll.EventHandlers[cah.$.LongPollEvent.GAME_PLAYER_LEAVE]=function(data){cah.longpoll.EventHandlers.__gameEvent(data,cah.Game.prototype.playerLeave,data[cah.$.LongPollResponse.NICKNAME],"player leave");};cah.longpoll.EventHandlers[cah.$.LongPollEvent.GAME_SPECTATOR_JOIN]=function(data){cah.longpoll.EventHandlers.__gameEvent(data,cah.Game.prototype.spectatorJoin,data[cah.$.LongPollResponse.NICKNAME],"spectator join (if you just joined a game this may be OK)");};cah.longpoll.EventHandlers[cah.$.LongPollEvent.GAME_SPECTATOR_LEAVE]=function(data){cah.longpoll.EventHandlers.__gameEvent(data,cah.Game.prototype.spectatorLeave,data[cah.$.LongPollResponse.NICKNAME],"spectator leave");};cah.longpoll.EventHandlers[cah.$.LongPollEvent.HAND_DEAL]=function(data){cah.longpoll.EventHandlers.__gameEvent(data,cah.Game.prototype.dealtCards,data[cah.$.LongPollResponse.HAND],"dealt cards");};cah.longpoll.EventHandlers[cah.$.LongPollEvent.GAME_STATE_CHANGE]=function(data){cah.longpoll.EventHandlers.__gameEvent(data,cah.Game.prototype.stateChange,data,"state change");};cah.longpoll.EventHandlers[cah.$.LongPollEvent.GAME_PLAYER_INFO_CHANGE]=function(data){cah.longpoll.EventHandlers.__gameEvent(data,cah.Game.prototype.updateUserStatus,data[cah.$.LongPollResponse.PLAYER_INFO],"player info change");};cah.longpoll.EventHandlers[cah.$.LongPollEvent.GAME_ROUND_COMPLETE]=function(data){cah.longpoll.EventHandlers.__gameEvent(data,cah.Game.prototype.roundComplete,data,"round complete");};cah.longpoll.EventHandlers[cah.$.LongPollEvent.GAME_WHITE_RESHUFFLE]=function(data){cah.longpoll.EventHandlers.__gameEvent(data,cah.Game.prototype.reshuffle,"white","white reshuffle");};cah.longpoll.EventHandlers[cah.$.LongPollEvent.GAME_BLACK_RESHUFFLE]=function(data){cah.longpoll.EventHandlers.__gameEvent(data,cah.Game.prototype.reshuffle,"black","black reshuffle");};cah.longpoll.EventHandlers[cah.$.LongPollEvent.GAME_JUDGE_LEFT]=function(data){cah.longpoll.EventHandlers.__gameEvent(data,cah.Game.prototype.judgeLeft,data,"judge left");};cah.longpoll.EventHandlers[cah.$.LongPollEvent.GAME_OPTIONS_CHANGED]=function(data){cah.longpoll.EventHandlers.__gameEvent(data,cah.Game.prototype.optionsChanged,data,"options changed");};cah.longpoll.EventHandlers[cah.$.LongPollEvent.HURRY_UP]=function(data){cah.longpoll.EventHandlers.__gameEvent(data,cah.Game.prototype.hurryUp,"","hurry up");};cah.longpoll.EventHandlers[cah.$.LongPollEvent.GAME_PLAYER_KICKED_IDLE]=function(data){cah.longpoll.EventHandlers.__gameEvent(data,cah.Game.prototype.playerKickedIdle,data,"idle kick");};cah.longpoll.EventHandlers[cah.$.LongPollEvent.GAME_PLAYER_SKIPPED]=function(data){cah.longpoll.EventHandlers.__gameEvent(data,cah.Game.prototype.playerSkipped,data,"player skip");};cah.longpoll.EventHandlers[cah.$.LongPollEvent.GAME_JUDGE_SKIPPED]=function(data){cah.longpoll.EventHandlers.__gameEvent(data,cah.Game.prototype.judgeSkipped,"","judge skip");};cah.longpoll.EventHandlers[cah.$.LongPollEvent.KICKED_FROM_GAME_IDLE]=function(data){var game=cah.currentGames[data[cah.$.LongPollResponse.GAME_ID]];if(game){game.dispose();delete cah.currentGames[data[cah.$.LongPollResponse.GAME_ID]];}
cah.GameList.instance.show();cah.GameList.instance.update();cah.log.error("You were kicked from game "+ data[cah.$.LongPollResponse.GAME_ID]
+" for being idle for too long.");};cah.longpoll.EventHandlers.__gameEvent=function(data,func,funcData,errorStr){var gameId=data[cah.$.LongPollResponse.GAME_ID];var game=cah.currentGames[gameId];if(game){func.call(game,funcData);}else{cah.log.error("Received "+ errorStr+" for unknown game id "+ gameId);}};cah.longpoll.EventHandlers[cah.$.LongPollEvent.CARDCAST_ADD_CARDSET]=function(data){cah.longpoll.EventHandlers.__gameEvent(data,cah.Game.prototype.addCardcastDeck,data,"add Cardcast");};cah.longpoll.EventHandlers[cah.$.LongPollEvent.CARDCAST_REMOVE_CARDSET]=function(data){cah.longpoll.EventHandlers.__gameEvent(data,cah.Game.prototype.removeCardcastDeck,data,"remove Cardcast");};