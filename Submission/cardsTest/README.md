To run our test suite, run the following command from within the cardsTest folder:

java --enable-preview -cp .:junit-4.12.jar:hamcrest-core-1.3.jar:cards.jar org.junit.runner.JUnitCore test.TestSuite



20 tests should pass as demonstrated here:

my-name@my-machine cardsTest % java --enable-preview -cp .:junit-4.12.jar:hamcrest-core-1.3.jar:cards.jar org.junit.runner.JUnitCore test.TestSuite
JUnit version 4.12
........Please enter the number of players: Please enter the pack file name: .Please enter the number of players: Please enter the pack file name: .Please enter the number of players: Please enter the pack file name: .Please enter the number of players: Please enter the pack file name: .........
Time: 0.039

OK (20 tests)

my-name@my-machine cardsTest % 