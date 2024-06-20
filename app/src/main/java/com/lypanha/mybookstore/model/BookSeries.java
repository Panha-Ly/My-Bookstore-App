package com.lypanha.mybookstore.model;

public class BookSeries {
    public static final String HARRY_POTTER_SERIES = "harry_potter_series";
    public static final String DUNE_SERIES = "dune_series";
    public static final String MAZE_RUNNER_SERIES = "the_maze_runner_series";
    public static final String HUNGER_GAMES_SERIES = "the_hunger_games_series";
    public static final String READY_PLAYER_ONE_SERIES = "ready_player_one_series";
    public static final String LORD_OF_THE_RINGS_SERIES = "the_lord_of_the_rings_series";


    private final String seriesId, name;

    public BookSeries(String id, String name) {
        this.seriesId = id;
        this.name = name;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public String getName() {
        return name;
    }
}
