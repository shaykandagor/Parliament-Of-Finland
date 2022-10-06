package fi.shaynek.parliamentfinland.data.models

import fi.shaynek.parliamentfinland.data.database.entity.MembersBasicData

class Parliament(private val members: List<MembersBasicData>) {
    // return all parties represented in parliament sorted by their name
    fun parties(): List<String> {
        return members.map { it.party }.toSet().toList().sorted()
    }

    // return all parties represented in parliament sorted by their number of members
    fun partiesBySize(): List<String> {
        return parties().map { Pair(it, partyMembers(it).size) }.sortedBy { -it.second }
            .map { it.first }
    }

    // return all members belonging to party sorted alphabetically by lastname, firstname
    fun partyMembers(party: String): List<MembersBasicData> {
        return members.filter { it.party == party }.sortedBy { it.lastName + it.firstName }
    }

    // return a map from party name (String) to members belonging to that party
    fun toPartyMap(): Map<String, List<MembersBasicData>> {
        return parties().map { it to partyMembers(it) }.toMap()
    }

    // return government parties, ie. parties that have at least one minister
    fun governmentParties(): Set<String> {
        return members.filter { it.minister }.map { it.party }.toSet()
    }

    /*fun govPartiesFromLargestPartytoSmallest(): List<String> {
        return governmentParties().toList().sortedBy { -toPartyMap().getOrDefault(it, listOf()).size }
    }*/
    fun governmentSeats() = members.count { governmentParties().contains(it.party) }
    fun seats(parties: Set<String>) = members.count { parties.contains(it.party) }
    fun governments(parties: Set<String>, known: Set<Set<String>> = emptySet()): Set<Set<String>> {
        if (seats(parties) <= 100) {
            return known
        } else {
            var sets: Set<Set<String>> = known + setOf(parties)
            for (p in parties) {
                sets += governments(parties.minus(p), sets)
            }
            return sets
        }
    }
}