package me.bristermitten.devdenbot.commands.roles

import com.jagrosh.jdautilities.command.CommandEvent
import me.bristermitten.devdenbot.commands.DevDenCommand
import me.bristermitten.devdenbot.discord.SELF_ROLES
import me.bristermitten.devdenbot.extensions.await
import me.bristermitten.devdenbot.inject.Used
import me.bristermitten.devdenbot.util.getSuggestion
import net.dv8tion.jda.api.JDA
import javax.inject.Inject

@Used
class RoleCommand @Inject constructor(
    val jda: JDA,
) : DevDenCommand(
    name = "role",
    help = "Give yourself a role",
    category = RoleCategory,
    aliases = arrayOf()
) {

    override suspend fun CommandEvent.execute() {
        if (args == ""){
            channel.sendMessage("Invalid use of the role command: Please specify a role.")
            return
        }
        val roles = jda.getRolesByName(args, true)
        val role = roles.firstOrNull { SELF_ROLES.contains(it.idLong) }
        if (role == null) {
            val suggestion = getSuggestion(
                args,
                SELF_ROLES.mapNotNull { jda.getRoleById(it.toString())?.name })
                ?.let { " Do you mean '$it'?" } ?: ""
            channel.sendMessage("Invalid role!$suggestion").await()
            return
        }
        if (message.guild.getMember(author)?.roles?.contains(role) == true) {
            channel.sendMessage("You already have this role!").await()
            return
        }
        message.guild.addRoleToMember(author.idLong, role).await()
        channel.sendMessage("Role added!").await()
    }

}
