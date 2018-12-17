package xcon.stackoverflow.shopping;

import xcon.stackoverflow.shopping.model.FoodItem;
import xcon.stackoverflow.shopping.model.ServiceUser;
import xcon.stackoverflow.shopping.exception.ShoppingServiceException;
import xcon.stackoverflow.shopping.model.User;
import xcon.stackoverflow.shopping.service.UserInfoService;
import xcon.stackoverflow.shopping.service.UserRepository;

import java.util.Map;
import java.util.Optional;

public class App {

    private UserInfoService userInfoService;
    private UserRepository userRepository;
    private ShoppingListService shoppingListService;

    public void execute() {
        User user = findUser(userInfoService.getUser()).orElseThrow(() -> new ShoppingServiceException("User not found"));
        Map<FoodItem, Long> shoppingList = shoppingListService.createShoppingList(user);
    }

    private Optional<User> findUser(ServiceUser serviceUser) {
        if (serviceUser != null) {
            return Optional.ofNullable(userRepository.findByLoginAndPassword(serviceUser.getLogin(), serviceUser.getPassword()));
        } else {
            return Optional.empty();
        }
    }
}
