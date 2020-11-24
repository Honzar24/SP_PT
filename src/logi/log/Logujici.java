package logi.log;

public interface Logujici extends Logovatelne {
    Logujici log(Logovatelne novaZprava);
}
