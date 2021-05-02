package me.bristermitten.devdenbot.xp.commands

import com.jagrosh.jdautilities.command.CommandEvent
import me.bristermitten.devdenbot.commands.DevDenCommand
import me.bristermitten.devdenbot.data.StatsUsers
import me.bristermitten.devdenbot.extensions.await
import me.bristermitten.devdenbot.extensions.commands.prepareReply
import javax.inject.Inject


/**
 * @author AlexL
 */
class ProfileCommand @Inject constructor(
) : DevDenCommand(
    name = "profile",
    help = "View your profile",
    aliases = arrayOf("stats")
) {

    override suspend fun CommandEvent.execute() {
        val targetUser = event.message.mentionedMembers.firstOrNull()?.user ?: event.message.author
        val statsUser = StatsUsers[targetUser.idLong]

        val action = prepareReply {
            title = "Your Statistics"
            field("XP", statsUser.xp.toString(), true)
            field("Level", statsUser.level.toString(), true)
            field("Disboard Bumps", statsUser.bumps.toString(), true)

            setFooter("Statistics for ${targetUser.name}")
        }

        action.await()
    }
}