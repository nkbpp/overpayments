package ru.pfr.overpayments.model.overpayment.entity.log;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LogiTest {

    private final Logi logi1 = new Logi(1L,null,"",TypeLog.Info,"");
    private final Logi logi2 = new Logi(2L,null,"",TypeLog.Info,"");

    @Test
    void compareToLess() {

        int i = logi1.compareTo(logi2);

        assertThat(i).isEqualTo(-1);
    }

    @Test
    void compareToMore() {

        int i = logi2.compareTo(logi1);

        assertThat(i).isEqualTo(1);
    }

    @Test
    void compareToEqual() {
        Logi logi3 = new Logi(1L,null,"",TypeLog.Info,"");

        int i = logi1.compareTo(logi3);

        assertThat(i).isEqualTo(0);
    }
}