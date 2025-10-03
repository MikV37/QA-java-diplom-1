package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

// Используем раннер Mockito для автоматической инициализации моков
@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    private Burger burger;

    // Создаем мок-объекты для зависимостей
    @Mock
    private Bun mockBun;
    @Mock
    private Ingredient mockIngredient1;
    @Mock
    private Ingredient mockIngredient2;

    @Before
    public void setUp() {
        burger = new Burger();
    }

    @Test
    public void setBunsShouldSetTheBun() {
        burger.setBuns(mockBun);
        assertEquals("Булочка не была установлена", mockBun, burger.bun);
    }

    @Test
    public void addIngredientShouldAddIngredientToList() {
        burger.addIngredient(mockIngredient1);
        assertFalse("Список ингредиентов не должен быть пустым", burger.ingredients.isEmpty());
        assertEquals("Размер списка должен быть 1", 1, burger.ingredients.size());
        assertEquals("Добавлен неверный ингредиент", mockIngredient1, burger.ingredients.get(0));
    }

    @Test
    public void removeIngredientShouldRemoveIngredientFromList() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.removeIngredient(0); // Удаляем первый ингредиент

        assertEquals("Размер списка должен быть 1 после удаления", 1, burger.ingredients.size());
        assertEquals("Оставшийся ингредиент неверен", mockIngredient2, burger.ingredients.get(0));
    }

    @Test
    public void moveIngredientShouldChangeOrder() {
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);
        burger.moveIngredient(0, 1); // Перемещаем первый на место второго

        assertEquals("Первый ингредиент должен быть mockIngredient2", mockIngredient2, burger.ingredients.get(0));
        assertEquals("Второй ингредиент должен быть mockIngredient1", mockIngredient1, burger.ingredients.get(1));
    }

    @Test
    public void getReceiptShouldReturnCorrectString() {
        // 1. Задаем поведение для моков (стабы)
        when(mockBun.getName()).thenReturn("Кратерная булка");
        when(mockBun.getPrice()).thenReturn(100.0f);

        when(mockIngredient1.getType()).thenReturn(IngredientType.SAUCE);
        when(mockIngredient1.getName()).thenReturn("Соус галактический");
        when(mockIngredient1.getPrice()).thenReturn(20.5f);

        when(mockIngredient2.getType()).thenReturn(IngredientType.FILLING);
        when(mockIngredient2.getName()).thenReturn("Котлета");
        when(mockIngredient2.getPrice()).thenReturn(150.0f);

        // 2. Выполняем действия
        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        // 3. Формируем ожидаемый результат
        float expectedPrice = 100.0f * 2 + 20.5f + 150.0f; // 370.5
        String expectedReceipt = String.format("(==== Кратерная булка ====)%n" +
                "= sauce Соус галактический =%n" +
                "= filling Котлета =%n" +
                "(==== Кратерная булка ====)%n" +
                "%nPrice: %f%n", expectedPrice);

        // 4. Проверяем результат
        String actualReceipt = burger.getReceipt();
        assertEquals("Сгенерированный чек не соответствует ожидаемому", expectedReceipt, actualReceipt);
    }
}
