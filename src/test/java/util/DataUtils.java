package util;

import ru.Sber.SberDiplomaPaper.domain.model.Product;
import ru.Sber.SberDiplomaPaper.domain.model.User;
import ru.Sber.SberDiplomaPaper.domain.model.UserRole;

public class DataUtils {

    public static User getAntonByTransient() {
        return  User.builder()
                .name("Antony")
                .email("Antony@yandex.ru")
                .passwordHash("8456111315")
                .role(UserRole.USER)
                .build();
    }
    public static User getFredByTransient() {
        return  User.builder()
                .name("Freddy")
                .email("Freddy@yandex.ru")
                .passwordHash("8456111315")
                .role(UserRole.USER)
                .build();
    }

    public static User getAntonByPersisted() {
        return User.builder()
                .id(1L)
                .name("Antony")
                .email("Antony@yandex.ru")
                .passwordHash("8456111315")
                .role(UserRole.USER)
                .build();
    }
    public static User getFredByPersisted() {
        return User.builder()
                .id(2L)
                .name("Freddy")
                .email("Freddy@yandex.ru")
                .passwordHash("8456111315")
                .role(UserRole.USER)
                .build();
    }

    public static Product getProductByTransient() {
        return Product.builder()
                .name("Bear bear")
                .description("Крафтовое пиво с апельсиновыми нотками")
                .price(100.0)
                .build();
    }
}
