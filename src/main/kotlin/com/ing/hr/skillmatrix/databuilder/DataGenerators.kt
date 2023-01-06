package com.ing.hr.skillmatrix.databuilder

import org.springframework.stereotype.Component

val ORGANIZATIONS = listOf(
    "ING",
    "Wholesale Banking",
    "Lending",
    "Trade",
    "Domestic Banking",
    "Retail Banking",
    "Private Banking",
    "Wealth Management",
    "Asset Management",
    "Investment Banking",
    "Corporate Finance",
    "Securities Trading",
    "Research",
    "Operations",
    "Human Resources",
    "Legal",
    "Compliance",
    "Risk Management",
    "Information Technology",
    "Marketing",
    "Customer Service"
)

@Component
class DataGenerators(
    private val nameGenerator: NameGenerator,
    private val lastNameGenerator: LastNameGenerator,
    private val projectGenerator: ProjectGenerator,
) {
    val name get() = nameGenerator
    val lastName get() = lastNameGenerator
    val project get() = projectGenerator
}

interface DataGenerator {
    fun generate(): String
}

@Component
class NameGenerator : DataGenerator {
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

    override fun generate(): String {
        return names.random()
    }
}

@Component
class LastNameGenerator : DataGenerator {
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

    override fun generate(): String {
        return surnames.random()
    }
}


@Component
class ProjectGenerator : DataGenerator {

    val adjectives = listOf(
        "Smart", "Fast", "Efficient", "Robust", "Secure", "Scalable",
        "Innovative", "Revolutionary", "Next-generation", "Cutting-edge", "Advanced",
        "Intelligent", "Powerful", "Dynamic", "Agile", "Flexible", "Versatile",
        "Customizable", "User-friendly", "Easy-to-use", "Elegant", "Refined",
        "Sleek", "Stylish", "Modern", "Contemporary", "Up-to-date", "Trendy"
    )
    val nouns = listOf(
        "Finance", "Investment", "Banking", "Trading", "Accounting", "Profitability",
        "Wealth", "Assets", "Capital", "Resources", "Funds", "Budget", "Revenue",
        "Profit", "Income", "Gains", "Savings", "Equity", "Return", "Yield",
        "Growth", "Expansion", "Development", "Improvement", "Advancement", "Enhancement",
        "Innovation", "Modernization", "Technology", "System", "Software", "App"
    )

    override fun generate(): String {
        return "${adjectives.random()} ${nouns.random()}"
    }

}