package com.lypanha.mybookstore;

import static com.lypanha.mybookstore.model.BookSeries.DUNE_SERIES;
import static com.lypanha.mybookstore.model.BookSeries.HARRY_POTTER_SERIES;
import static com.lypanha.mybookstore.model.BookSeries.HUNGER_GAMES_SERIES;
import static com.lypanha.mybookstore.model.BookSeries.LORD_OF_THE_RINGS_SERIES;
import static com.lypanha.mybookstore.model.BookSeries.MAZE_RUNNER_SERIES;
import static com.lypanha.mybookstore.model.BookSeries.READY_PLAYER_ONE_SERIES;


import com.lypanha.mybookstore.model.Book;
import com.lypanha.mybookstore.model.BookSeries;
import com.lypanha.mybookstore.model.Item;
import com.lypanha.mybookstore.model.User;

import java.util.ArrayList;

public class Utils {
    private static Utils instance;

    private ArrayList<Book> allBooks; // books list
    private ArrayList<BookSeries> allBookSeriesNames; // series names list
    private ArrayList<Item> allBooksBySeries; // books with series names list
    private ArrayList<Book> booksInCart; // books in cart list

    private ArrayList<User> users;


    private Utils() {
        if(null == allBooks && null == allBookSeriesNames && null == allBooksBySeries && null == booksInCart && null == users){
            initData();
        }
    }

    public static Utils getInstance() {
        if (null == instance) {
            instance = new Utils();
        }
        return instance;
    }

    public ArrayList<Book> getAllBooks() {
        return allBooks;
    }

    public ArrayList<Item> getAllBooksBySeries() {
        return allBooksBySeries;
    }
    public ArrayList<Book> getBooksInCart() {
        return booksInCart;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public Book getBookById(int bookId) {
        for (Book book :
                allBooks) {
            if(book.getId() == bookId) return book;
        }
        return null;
    }
    public BookSeries getBookSeriesById(String bookSeriesId){
        for (BookSeries bookSeries :
                allBookSeriesNames) {
            if(bookSeries.getSeriesId().equals(bookSeriesId)) return bookSeries;
        }
        return null;
    }


    private void initData() {
        allBooks = new ArrayList<>();
        allBookSeriesNames = new ArrayList<>();
        allBooksBySeries = new ArrayList<>();
        booksInCart = new ArrayList<>();
        users = new ArrayList<>();

        users.add(new User(1,R.drawable.pfp_bmo,"Panha Ly","panha@gmail.com", "123456789", User.LOGGED_OUT));
        users.add(new User(1,R.drawable.pfp_admin,"Administrator","admin", "12345", User.LOGGED_OUT));


        // Dune Books
        allBooks.add(new Book(1,DUNE_SERIES,"Dune","Frank Herbert","1965",
                "Dune is set on the desert planet Arrakis approximately 20,000 years in the future. The year is 10,191, and human beings have colonized planets throughout the universe. The story follows Paul Atreides, the heir to a noble family tasked with ruling Arrakis—a harsh world where the valuable spice called melange is the only resource of significance.",
                "5.99",R.drawable.img_dune_1));
        allBooks.add(new Book(2,DUNE_SERIES,"Dune Messiah","Frank Herbert","1969",
                "Twelve years after the events described in Dune, Paul Atreides rules as Emperor of the Known Universe, following Muad'dib's Jihad which he unleashed by accepting the role of Mahdi to the Fremen. While Paul is the most powerful Emperor ever, he is ironically powerless to stop the lethal religious excesses of the juggernaut he has created.",
                "5.99",R.drawable.img_dune_2));
        allBooks.add(new Book(3,DUNE_SERIES,"Children of Dune","Frank Herbert","April 1976",
                "At the end of Dune Messiah, Paul Atreides walks into the desert, a blind man, leaving his twin children Leto and Ghanima in the care of the Fremen, while his sister Alia rules the universe as regent. Awakened in the womb by the spice, the children are the heirs to Paul's prescient vision of the fate of the universe, a role that Alia desperately craves. House Corrino schemes to return to the throne, while the Bene Gesserit make common cause with the Tleilaxu and Spacing Guild to gain control of the spice and the children of Paul Atreides.",
                "5.99",R.drawable.img_dune_3));
        allBooks.add(new Book(4,DUNE_SERIES,"God Emperor of Dune","Frank Herbert","1981",
                "3,500 years have passed since Paul Atreides became the messiah of the Fremen and the Emperor of the universe. His son, Leto II, sees the path his father Muad'dib saw, a future that avoided the extinction of human life. That future, however, required the ultimate and monstrous act of selflessness of becoming a metamorphic vector between primate and worm.",
                "5.99",R.drawable.img_dune_4));
        allBooks.add(new Book(5,DUNE_SERIES,"Heretics of Dune","Frank Herbert","1984",
                "Set 1,500 years after the events of God Emperor of Dune (1981), the novel finds humanity on the path set for them by the tyrant Leto II Atreides to guarantee their survival. But a new threat arrives in the form of the Honored Matres, a brutal matriarchy from beyond the known universe whose only goals are conquest and destruction.",
                "5.99",R.drawable.img_dune_5));
        allBooks.add(new Book(6,DUNE_SERIES,"Chapterhouse: Dune","Frank Herbert","April 1985",
                "A direct follow-up to Heretics of Dune, the novel chronicles the continued struggles of the Bene Gesserit sisterhood against the violent Honored Matres, who are succeeding in their bid to seize control of the universe and destroy the factions and planets that oppose them.",
                "5.99",R.drawable.img_dune_6));

        // Harry Potter Books
        allBooks.add(new Book(7,HARRY_POTTER_SERIES,"Harry Potter and the Philosopher's Stone","J. K. Rowling","26 June 1997",
                "Harry Potter, an orphaned boy who discovers he is a wizard. He receives an invitation to attend Hogwarts School of Witchcraft and Wizardry, where he learns about magic, makes friends, and uncovers secrets about his past.",
                "5.99",R.drawable.img_hp_1));
        allBooks.add(new Book(8,HARRY_POTTER_SERIES,"Harry Potter and the Chamber of Secrets","J. K. Rowling","2 July 1998",
                "Harry returns to Hogwarts for his second year. Mysterious events occur, including attacks by a monster within the school. Harry investigates the Chamber of Secrets and faces danger alongside his friends.",
                "5.99",R.drawable.img_hp_2));
        allBooks.add(new Book(9,HARRY_POTTER_SERIES,"Harry Potter and the Prisoner of Azkaban","J. K. Rowling","8 July 1999",
                "Harry learns about his godfather, Sirius Black, who escaped from the notorious wizard prison, Azkaban. He also encounters Dementors and discovers the truth about his parents.",
                "5.99",R.drawable.img_hp_3));
        allBooks.add(new Book(10,HARRY_POTTER_SERIES,"Harry Potter and the Goblet of Fire","J. K. Rowling","8 July 2000",
                "Hogwarts hosts the Triwizard Tournament, a dangerous magical competition. Harry unexpectedly becomes a participant, facing dragons, underwater challenges, and a mysterious maze.",
                "5.99",R.drawable.img_hp_4));
        allBooks.add(new Book(11,HARRY_POTTER_SERIES,"Harry Potter and the Order of the Phoenix","J. K. Rowling","21 June 2003",
                "Harry and his friends form Dumbledore’s Army to resist the oppressive Ministry of Magic. They learn about a prophecy and face the return of the dark wizard, Lord Voldemort.",
                "5.99",R.drawable.img_hp_5));
        allBooks.add(new Book(12,HARRY_POTTER_SERIES,"Harry Potter and the Half-Blood Prince","J. K. Rowling","16 July 2005",
                "Harry delves into Voldemort’s past, uncovers secrets about Horcruxes. The wizarding world prepares for war.",
                "5.99",R.drawable.img_hp_6));
        allBooks.add(new Book(13,HARRY_POTTER_SERIES,"Harry Potter and the Deathly Hallows","J. K. Rowling","21 July 2007",
                "Harry, Ron, and Hermione embark on a quest to find and destroy Voldemort’s Horcruxes. The final battle looms, and Harry learns about the Elder Wand and his destiny.",
                "5.99",R.drawable.img_hp_7));

        // Maze Runner Books
        allBooks.add(new Book(14,MAZE_RUNNER_SERIES,"The Maze Runner","James Dashner","October 6, 2009",
                "Set in a post-apocalyptic world, young Thomas is deposited in a community of boys after his memory is erased, soon learning they're all trapped in a maze that will require him to join forces with fellow “runners” for a shot at escape.",
                "5.99",R.drawable.img_maze_runner_1));
        allBooks.add(new Book(15,MAZE_RUNNER_SERIES,"The Scorch Trials","James Dashner","18 September 2010",
                "Thomas and his fellow Gladers face their greatest challenge yet: searching for clues about the mysterious and powerful organization known as WCKD. Their journey takes them to the Scorch, a desolate landscape filled with unimaginable obstacles. Teaming up with resistance fighters, the Gladers take on WCKD’s vastly superior forces and uncover its shocking plans for them all.",
                "5.99",R.drawable.img_maze_runner_2));
        allBooks.add(new Book(16,MAZE_RUNNER_SERIES,"The Death Cure","James Dashner","2011",
                "Thomas leads his group of escaped Gladers on their final and most dangerous mission yet. To save their friends and find a cure for a deadly disease known as \"The Flare\", they must break into the legendary Last City, a WCKD-controlled labyrinth that may turn out to be the deadliest maze of all. Anyone who makes it out alive will get answers to the questions the Gladers have been asking since they first arrived in the maze.",
                "5.99",R.drawable.img_maze_runner_3));
        allBooks.add(new Book(17,MAZE_RUNNER_SERIES,"The Kill Order","James Dashner","August 14, 2012",
                "Set thirteen years before the prologue and the events of The Maze Runner, the story follows a group of individuals struggling to survive in the year after the Earth is ravaged by catastrophic solar flares, all the while attempting to avert becoming victims of a deadly virus that is ravaging the eastern United States.",
                "5.99",R.drawable.img_maze_runner_4));
        allBooks.add(new Book(18,MAZE_RUNNER_SERIES,"The Fever Code","James Dashner","September 27, 2016",
                "Thirteen years after the events of The Kill Order, the world has ended: the earth is scorched, and fever rages through the population. Out of the chaos, a boy emerges with the power to change everything: Thomas. This is the start of his story.",
                "5.99",R.drawable.img_maze_runner_5));

        // Ready Player One Books
        allBooks.add(new Book(19,READY_PLAYER_ONE_SERIES,"Ready Player One","Ernest Cline","August 16, 2011",
                "Set in a dystopia in 2045, the world has been gripped by an energy crisis from the depletion of fossil fuels and the consequences of pollution, global warming and overpopulation. To escape the decline their world is facing, people turn to the OASIS, a virtual universe accessible by players using visors and haptic technology such as gloves. The story follows protagonist Wade Watts on his search for an Easter egg in a worldwide virtual reality game, the discovery of which would lead him to inherit the game creator's fortune and the game itself."
                ,"5.99",R.drawable.img_ready_player_1));
        allBooks.add(new Book(20,READY_PLAYER_ONE_SERIES, "Ready Player Two","Ernest Cline","November 24, 2020",
                "After winning control of the OASIS, Wade discovers an advanced virtual reality headset and subsequently a new quest for a mysterious prize."
                ,"5.99",R.drawable.img_ready_player_2));

        // The Hunger Games Books
        allBooks.add(new Book(21,HUNGER_GAMES_SERIES,"The Hunger Games","Suzanne Collins","September 14, 2008",
                "Every year in the ruins of what was once North America, the nation of Panem forces each of its twelve districts to send a teenage boy and girl to compete in the Hunger Games. Part twisted entertainment, part government intimidation tactic, the Hunger Games are a nationally televised event in which “Tributes” must fight with one another until one survivor remains. Pitted against highly-trained Tributes who have prepared for these Games their entire lives, Katniss is forced to rely upon her sharp instincts as well as the mentorship of drunken former victor Haymitch Abernathy. If she’s ever to return home to District 12, Katniss must make impossible choices in the arena that weigh survival against humanity and life against love. The world will be watching.",
                "5.99",R.drawable.img_hunger_games_1));
        allBooks.add(new Book(22,HUNGER_GAMES_SERIES,"Catching Fire","Suzanne Collins","September 1, 2009",
                "Katniss Everdeen has returned home safe after winning the 74th Annual Hunger Games along with fellow tribute Peeta Mellark. Winning means that they must turn around and leave their family and close friends, embarking on a \"Victor's Tour\" of the districts. Along the way Katniss senses that a rebellion is simmering, but the Capitol is still very much in control as President Snow prepares the 75th Annual Hunger Games (The Quarter Quell) - a competition that could change Panem forever.",
                "5.99",R.drawable.img_hunger_games_2));
        allBooks.add(new Book(23,HUNGER_GAMES_SERIES,"Mockingjay","Suzanne Collins","August 24, 2010",
                "Katniss Everdeen reluctantly becomes the symbol of a mass rebellion against the autocratic Capitol. With the nation of Panem in a full scale war, Katniss confronts President Snow in the final showdown. Teamed with a group of her closest friends – including Gale, Finnick, and Peeta – Katniss goes off on a mission with the unit from District 13 as they risk their lives to stage an assassination attempt on President Snow who has become increasingly obsessed with destroying her. The mortal traps, enemies, and moral choices that await Katniss will challenge her more than any arena she faced in The Hunger Games.",
                "5.99",R.drawable.img_hunger_games_3));
        allBooks.add(new Book(24,HUNGER_GAMES_SERIES,"The Ballad of Songbirds and Snakes","Suzanne Collins","May 19, 2020",
                "64 years before he becomes the tyrannical president of Panem, Coriolanus Snow sees a chance for a change in fortunes when he mentors Lucy Gray Baird, the female tribute from District 12.",
                "5.99",R.drawable.img_hunger_games_4));

        // The Lord of the Rings Books
        allBooks.add(new Book(25,LORD_OF_THE_RINGS_SERIES,"The Fellowship of the Ring","J. R. R. Tolkien","29 July 1954",
                "Young hobbit Frodo Baggins, after inheriting a mysterious ring from his uncle Bilbo, must leave his home in order to keep it from falling into the hands of its evil creator. Along the way, a fellowship is formed to protect the ringbearer and make sure that the ring arrives at its final destination: Mt. Doom, the only place where it can be destroyed.",
                "5.99",R.drawable.img_lotr_1));
        allBooks.add(new Book(26,LORD_OF_THE_RINGS_SERIES,"The Two Towers","J. R. R. Tolkien","11 November 1954",
                "Frodo and Sam are trekking to Mordor to destroy the One Ring of Power while Gimli, Legolas and Aragorn search for the orc-captured Merry and Pippin. All along, nefarious wizard Saruman awaits the Fellowship members at the Orthanc Tower in Isengard.",
                "5.99",R.drawable.img_lotr_2));
        allBooks.add(new Book(27,LORD_OF_THE_RINGS_SERIES,"The Return of the King","J. R. R. Tolkien","20 October 1955",
                "Aragorn is revealed as the heir to the ancient kings as he, Gandalf and the other members of the broken fellowship struggle to save Gondor from Sauron's forces. Meanwhile, Frodo and Sam bring the ring closer to the heart of Mordor, the dark lord's realm.",
                "5.99",R.drawable.img_lotr_3));


        // All series names list
        allBookSeriesNames.add(new BookSeries(DUNE_SERIES,"Dune"));
        allBookSeriesNames.add(new BookSeries(HARRY_POTTER_SERIES,"Harry Potter"));
        allBookSeriesNames.add(new BookSeries(READY_PLAYER_ONE_SERIES,"Ready Player One"));
        allBookSeriesNames.add(new BookSeries(HUNGER_GAMES_SERIES,"The Hunger Games"));
        allBookSeriesNames.add(new BookSeries(LORD_OF_THE_RINGS_SERIES,"The Lord of the Rings"));
        allBookSeriesNames.add(new BookSeries(MAZE_RUNNER_SERIES,"The Maze Runner"));


        // All books by series in alphabetical order list
        for (BookSeries series :
                allBookSeriesNames) {
            allBooksBySeries.add(new Item(Item.BOOK_SERIES,series));
            for (Book book :
                    allBooks) {
                if (book.getSeriesId().equals(series.getSeriesId())) {
                    allBooksBySeries.add(new Item(Item.BOOK, book));
                }
            }
        }

    }

    public boolean removeFromCart(int bookId) {
        for (Book book :
                booksInCart) {
            if (book.getId() == bookId) {
                return booksInCart.remove(book);
            }
        }
        return false;
    }
    public boolean addToCart(int bookId) {
        for (Book book :
                booksInCart) {
            if (book.getId() == bookId) {
                return false;
            }
        }
        for (Book book :
                allBooks) {
            if (book.getId() == bookId) {
                booksInCart.add(book);
                return true;
            }
        }
        return false;
    }


}

