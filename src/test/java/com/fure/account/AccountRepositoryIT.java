package com.fure.account;

import com.fure.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountRepositoryIT {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @Rollback(false)
    public void testSaveAccount() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");

        Account account = new Account();
        account.setUser(user);
        account.setBalance(BigDecimal.valueOf(1000.0));

        Account savedAccount = accountRepository.save(account);

        assertThat(savedAccount).isNotNull();
        assertThat(savedAccount.getId()).isGreaterThan(0);
    }

    @Test
    public void testFindAccountById() {
        Long accountId = 1L;
        Optional<Account> account = accountRepository.findById(accountId);

        assertThat(account).isPresent();
        assertThat(account.get().getId()).isEqualTo(accountId);
    }

    @Test
    @Rollback(false)
    public void testDeleteAccount() {
        Long accountId = 1L;
        boolean existsBeforeDelete = accountRepository.findById(accountId).isPresent();

        accountRepository.deleteById(accountId);

        boolean notExistsAfterDelete = accountRepository.findById(accountId).isPresent();

        assertThat(existsBeforeDelete).isTrue();
        assertThat(notExistsAfterDelete).isFalse();
    }

}
