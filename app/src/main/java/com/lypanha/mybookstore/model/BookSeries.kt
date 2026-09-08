package com.lypanha.mybookstore.model

data class BookSeries(
    val seriesId: String,
    val name: String
) {
    companion object {
        const val HARRY_POTTER_SERIES: String = "harry_potter_series"
        const val DUNE_SERIES: String = "dune_series"
        const val MAZE_RUNNER_SERIES: String = "the_maze_runner_series"
        const val HUNGER_GAMES_SERIES: String = "the_hunger_games_series"
        const val READY_PLAYER_ONE_SERIES: String = "ready_player_one_series"
        const val LORD_OF_THE_RINGS_SERIES: String = "the_lord_of_the_rings_series"

    }
}
