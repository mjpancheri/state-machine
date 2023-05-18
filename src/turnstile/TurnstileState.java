package turnstile;

public interface TurnstileState {
    void pass(TurnstileFSM fsm);

    void coin(TurnstileFSM fsm);
}
