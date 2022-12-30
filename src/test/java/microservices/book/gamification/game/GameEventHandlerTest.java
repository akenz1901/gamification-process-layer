package microservices.book.gamification.game;

import microservices.book.gamification.challenge.ChallengeSolvedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class GameEventHandlerTest {

    private GameEventHandler gameEventHandler;

    @Mock
    private GameService gameService;

    @BeforeEach
    void setUp() {
        gameEventHandler = new GameEventHandler(gameService);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void consumeAttempt(boolean correct){

        //given
        ChallengeSolvedEvent event = createChallengeSolvedEvent(correct);

        //when
        gameEventHandler.handleMultiplicationSolved(event);

        //then
        var eventCaptor = ArgumentCaptor.forClass(ChallengeSolvedEvent.class);
        verify(gameService).newAttemptForUser(eventCaptor.capture());
        then(eventCaptor.getValue()).isEqualTo(event);
    }

    private ChallengeSolvedEvent createChallengeSolvedEvent(boolean isCorrect) {
        return new ChallengeSolvedEvent(1L, isCorrect, 30, 50, 10L, "John");
    }
}