package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    private Burger burger;

    @Mock
    private Bun mockBun;
    // Новые имена вместо mockIngredient1 и mockIngredient2
    @Mock
    private Ingredient mockSauce;
    @Mock
    private Ingredient mockFilling;

    @Before
    public void setUp() {
        burger = new Burger();
    }

    @Test
    public void setBunsShouldSetTheBun() {
        burger.setBuns(mockBun);
        // Проверяем только одно: что булочка установилась
        assertEquals("Булочка не была установлена", mockBun, burger.bun);
    }

    // Тест addIngredient разбит на два
    @Test
    public void addIngredientShouldIncreaseListSize() {
        burger.addIngredient(mockSauce);
        // Проверяем только одно: что размер списка стал равен 1
        assertEquals("Размер списка должен быть 1", 1, burger.ingredients.size());
    }

    @Test
    public void addIngredientShouldAddCorrectIngredient() {
        burger.addIngredient(mockSauce);
        // Проверяем только одно: что в списке лежит правильный ингредиент
        assertSame("Добавлен неверный ингредиент", mockSauce, burger.ingredients.get(0));
    }

    // Тест removeIngredient разбит на два
    @Test
    public void removeIngredientShouldDecreaseListSize() {
        burger.addIngredient(mockSauce);
        burger.addIngredient(mockFilling);
        burger.removeIngredient(0);
        // Проверяем только одно: что размер списка уменьшился
        assertEquals("Размер списка должен быть 1 после удаления", 1, burger.ingredients.size());
    }

    @Test
    public void removeIngredientShouldRemoveCorrectIngredient() {
        burger.addIngredient(mockSauce);
        burger.addIngredient(mockFilling);
        burger.removeIngredient(0); // Удаляем соус
        // Проверяем только одно: что в списке осталась начинка
        assertSame("Оставшийся ингредиент неверен", mockFilling, burger.ingredients.get(0));
    }

    // Тест moveIngredient разбит на два
    @Test
    public void moveIngredientShouldPlaceIngredientAtNewIndex() {
        burger.addIngredient(mockSauce);
        burger.addIngredient(mockFilling);
        burger.moveIngredient(0, 1); // Перемещаем соус в конец
        // Проверяем только одно: что соус теперь на новой позиции
        assertSame("Соус должен быть на новой позиции", mockSauce, burger.ingredients.get(1));
    }

    @Test
    public void moveIngredientShouldShiftOtherIngredient() {
        burger.addIngredient(mockSauce);
        burger.addIngredient(mockFilling);
        burger.moveIngredient(0, 1); // Перемещаем соус
        // Проверяем только одно: что начинка сдвинулась на место соуса
        assertSame("Начинка должна была сдвинуться", mockFilling, burger.ingredients.get(0));
    }

    @Test
    public void getReceiptShouldReturnCorrectString() {
        // Подготовка
        when(mockBun.getName()).thenReturn("Краторная булка");
        when(mockBun.getPrice()).thenReturn(100.0f);
        when(mockSauce.getType()).thenReturn(IngredientType.SAUCE);
        when(mockSauce.getName()).thenReturn("Соус галактический");
        when(mockSauce.getPrice()).thenReturn(20.5f);
        when(mockFilling.getType()).thenReturn(IngredientType.FILLING);
        when(mockFilling.getName()).thenReturn("Котлета");
        when(mockFilling.getPrice()).thenReturn(150.0f);

        burger.setBuns(mockBun);
        burger.addIngredient(mockSauce);
        burger.addIngredient(mockFilling);

        float expectedPrice = 100.0f * 2 + 20.5f + 150.0f;
        String expectedReceipt = String.format("(==== Краторная булка ====)%n" +
                "= sauce Соус галактический =%n" +
                "= filling Котлета =%n" +
                "(==== Краторная булка ====)%n" +
                "%nPrice: %f%n", expectedPrice);

        // Действие
        String actualReceipt = burger.getReceipt();

        // Проверка (здесь одна проверка — это нормально, т.к. мы проверяем один сложный результат)
        assertEquals("Сгенерированный чек не соответствует ожидаемому", expectedReceipt, actualReceipt);
    }
}