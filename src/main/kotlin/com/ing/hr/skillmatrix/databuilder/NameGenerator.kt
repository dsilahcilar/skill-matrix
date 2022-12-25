package com.ing.hr.skillmatrix.databuilder

import org.springframework.stereotype.Service


@Service
class NameGenerator {
    private val names = listOf(
        "Alice", "Bob", "Charlie", "Dave", "Eve", "Frank", "Greta", "Hannah", "Igor", "Jenny",
        "Karen", "Larry", "Mandy", "Nancy", "Olivia", "Pam", "Quincy", "Rachel", "Sandy", "Tina",
        "Ursula", "Vicky", "Wendy", "Xena", "Yvonne", "Zara", "Aiden", "Brent", "Cody", "Drew",
        "Ethan", "Franklin", "Gage", "Harley", "Ian", "Jax", "Keaton", "Landon", "Maddox", "Nash",
        "Owen", "Peyton", "Quinn", "Ryder", "Sawyer", "Tanner", "Usher", "Vance", "Wyatt", "Xander",
        "Yardley", "Zeke", "Abby", "Brielle", "Cora", "Daisy", "Ella", "Fiona", "Gia", "Haley",
        "Ivy", "Jazmine", "Kendall", "Lila", "Mckenna", "Natalie", "Olive", "Paige", "Quinn", "Riley",
        "Samantha", "Tessa", "Uma", "Violet", "Willow", "Ximena", "Yara", "Zaria"
    )

    private val surnames = listOf(
        "Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor",
        "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez", "Robinson",
        "Rodriguez", "Lewis", "Lee", "Walker", "Hall", "Allen", "Young", "Hernandez", "King", "Wright", "Lopez",
        "Hill", "Scott", "Green", "Adams", "Baker", "Gonzalez", "Nelson", "Carter", "Mitchell", "Perez",
        "Roberts", "Turner", "Phillips", "Campbell", "Parker", "Evans", "Edwards", "Collins", "Stewart", "Sanchez",
        "Morris", "Rogers", "Reed", "Cook", "Morgan", "Bell", "Murphy", "Bailey", "Rivera", "Cooper", "Clark",
        "Richardson", "Cox", "Howard", "Ward", "Torres", "Peterson", "Gray", "Ramirez", "James", "Watson",
        "Brooks", "Kelly", "Sanders", "Price", "Bennett", "Wood", "Barnes", "Ross", "Henderson", "Coleman"
    )

    fun generateRandomName(): String {
        val randomIndex = (0 until names.size).random()
        return names[randomIndex]
    }

    fun generateRandomSurname(): String {
        val randomIndex = (0 until surnames.size).random()
        return surnames[randomIndex]
    }
}