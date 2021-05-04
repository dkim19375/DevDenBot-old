@file:UseSerializers(BigIntegerSerializer::class)

package me.bristermitten.devdenbot.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.UseSerializers
import me.bristermitten.devdenbot.serialization.BigIntegerSerializer
import me.bristermitten.devdenbot.serialization.PrettyName
import me.bristermitten.devdenbot.stats.GlobalStats
import org.apache.commons.collections4.queue.CircularFifoQueue
import java.math.BigInteger

/**
 * @author AlexL
 */
@Serializable
data class StatsUser(
    val userId: Long,
    @PrettyName("XP")
    var xp: BigInteger = BigInteger.ZERO,
    var level: Int = 0,
    var bumps: Int = 0,
) {
    @Transient
    val recentMessages = CircularFifoQueue<CachedMessage>(10)

    var lastMessageSentTime: Long = -1

    fun giveXP(amount: BigInteger) {
        this.xp += amount
        GlobalStats.xpGiven += amount
    }

}
