package praktikum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

// Используем раннер для параметризованных тестов
@RunWith(Parameterized.class)
public class BurgerPriceParameterizedTest {

    private final Bun bun;
    private final Ingredient[] ingredients;
    private final float expectedPrice;

    // Конструктор, который будет вызываться для каждого набора параметров
    public BurgerPriceParameterizedTest(Bun bun, Ingredient[] ingredients, float expectedPrice) {
        this.bun = bun;
        this.ingredients = ingredients;
        this.expectedPrice = expectedPrice;
    }

    // Метод, который поставляет данные для теста
    @Parameterized.Parameters(name = "Цена бургера с булочкой \"{0}\" и {1} ингредиентами")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                // bun, ingredients, expectedPrice
                {
                        new Bun("Простая", 10.0f),
                        new Ingredient[]{},
                        20.0f // 10 * 2
                },
                {
                        new Bun("Дорогая", 100.0f),
                        new Ingredient[]{
                                new Ingredient(IngredientType.SAUCE, "Соус", 50.0f)
                        },
                        250.0f // 100 * 2 + 50
                },
                {
                        new Bun("VIP", 125.5f),
                        new Ingredient[]{
                                new Ingredient(IngredientType.FILLING, "Котлета", 200.0f),
                                new Ingredient(IngredientType.SAUCE, "Кетчуп", 25.5f)
                        },
                        476.5f // 125.5 * 2 + 200 + 25.5
                }
        });
    }

    @Test
    public void getPriceShouldCalculateCorrectly() {
        Burger burger = new Burger();
        burger.setBuns(bun);
        for (Ingredient ingredient : ingredients) {
            burger.addIngredient(ingredient);
        }

        assertEquals("Расчет цены неверен", expectedPrice, burger.getPrice(), 0.001f);
    }
}
