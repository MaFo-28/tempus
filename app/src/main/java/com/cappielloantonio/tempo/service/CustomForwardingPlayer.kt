package com.cappielloantonio.tempo.service

import androidx.media3.common.*
import androidx.media3.common.util.UnstableApi

@UnstableApi
open class CustomForwardingPlayer(player: Player) : ForwardingPlayer(player) {
    override fun getAvailableCommands(): Player.Commands {
        return if (mediaItemCount > 0) {
            super.getAvailableCommands().buildUpon()
                .add(COMMAND_SEEK_TO_NEXT)
                .add(COMMAND_SEEK_TO_NEXT_MEDIA_ITEM)
                .add(COMMAND_SEEK_TO_PREVIOUS)
                .add(COMMAND_SEEK_TO_PREVIOUS_MEDIA_ITEM)
                .build()
        } else {
            super.getAvailableCommands()
        }
    }

    override fun isCommandAvailable(command: Int): Boolean {
        return when (command) {
            COMMAND_SEEK_TO_NEXT,
            COMMAND_SEEK_TO_NEXT_MEDIA_ITEM,
            COMMAND_SEEK_TO_PREVIOUS,
            COMMAND_SEEK_TO_PREVIOUS_MEDIA_ITEM -> mediaItemCount > 0
            else -> super.isCommandAvailable(command)
        }
    }

    //override fun hasNextMediaItem(): Boolean = mediaItemCount > 0
    //override fun hasPreviousMediaItem(): Boolean = mediaItemCount > 0

    override fun seekToNext() = handleSeekNext()
    override fun seekToNextMediaItem() = handleSeekNext()

    private fun handleSeekNext() {
        if (super.hasNextMediaItem()) {
            super.seekToNextMediaItem()
        }/* else if (mediaItemCount > 0) {
            seekToDefaultPosition(0)
        }*/
    }

    override fun seekToPrevious() = handleSeekPrevious()
    override fun seekToPreviousMediaItem() = handleSeekPrevious()

    private fun handleSeekPrevious() {
        if (currentPosition > 3000) {
            seekTo(0)
        } else if (super.hasPreviousMediaItem()) {
            super.seekToPreviousMediaItem()
        }/* else if (mediaItemCount > 0) {
            seekToDefaultPosition(mediaItemCount - 1)
        }*/
    }
}