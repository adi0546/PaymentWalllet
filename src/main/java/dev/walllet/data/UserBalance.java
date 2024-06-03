package dev.wallet.data;
import org.springframework.stereotype.Component;
import dev.wallet.model.Amount;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserBalance {
    private Map<String, Amount> userBalances = new HashMap<>();

    public Map<String, Amount> getUserBalances() {
        return userBalances;
    }
}