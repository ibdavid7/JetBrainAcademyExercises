package machine;

import java.util.Scanner;

public class CoffeeMachine {
    private int money;
    private int water;
    private int milk;
    private int coffee;
    private int cups;
    private Scanner scanner;

    public CoffeeMachine() {
        this(550, 400, 540, 120, 9);
    }

    public CoffeeMachine(int money, int waterCapacity, int milkCapacity, int coffeeBeanCapacity, int disposableCupsCapacity) {
        this.money = money;
        this.water = waterCapacity;
        this.milk = milkCapacity;
        this.coffee = coffeeBeanCapacity;
        this.cups = disposableCupsCapacity;
        this.scanner = new Scanner(System.in);
    }

    private void adjustMoney(int n) {
        this.money += n;
    }

    private void adjustWater(int n) {
        this.water += n;
    }

    private void adjustMilk(int n) {
        this.milk += n;
    }

    private void adjustCoffee(int n) {
        this.coffee += n;
    }

    private void adjustCups(int n) {
        this.cups += n;
    }

    private int getMoney() {
        return this.money;
    }

    public int getWater() {
        return this.water;
    }

    public int getMilk() {
        return this.milk;
    }

    public int getCoffee() {
        return this.coffee;
    }

    public int getCups() {
        return this.cups;
    }

    public boolean checkCapacity(int water, int milk, int coffee, int cups) {
        return this.water >= water && this.milk >= milk && this.coffee >= coffee && this.cups >= cups;
    }

    public String missingIngredient(int water, int milk, int coffee, int cups) {
        if (this.water < water) {
            return "water";
        } else if (this.milk < milk) {
            return "milk";
        } else if (this.coffee < coffee) {
            return "coffee";
        } else if (this.cups < cups) {
            return "cups";
        } else {
            return "";
        }
    }

    private boolean buyCoffee(int water, int milk, int coffee, int cups, int money) {
        if (checkCapacity(water, milk, coffee, cups)) {
            adjustWater(-water);
            adjustMilk(-milk);
            adjustCoffee(-coffee);
            adjustCups(-cups);
            adjustMoney(money);
            System.out.println("I have enough resources, making you a coffee!");
            return true;
        } else {
            System.out.println(String.format("Sorry, not enough %s!",
                    missingIngredient(water, milk, coffee, cups)));
            return false;
        }
    }

    public boolean buyEspresso() {
        return buyCoffee(250, 0, 16, 1, 4);
    }

    public boolean buyLatte() {
        return buyCoffee(350, 75, 20, 1, 7);
    }

    public boolean buyCappuccino() {
        return buyCoffee(200, 100, 12, 1, 6);
    }

    public void fill(int water, int milk, int coffee, int cups) {
        adjustWater(water);
        adjustMilk(milk);
        adjustCoffee(coffee);
        adjustCups(cups);
    }

    public String getState() {
        return String.format("The coffee machine has:\n" +
                "%d of water\n" +
                "%d of milk\n" +
                "%d of coffee beans\n" +
                "%d of disposable cups\n" +
                "%d of money\n", water, milk, coffee, cups, money);
    }

    enum Action {
        BUY("buy"),
        FILL("fill"),
        TAKE("take"),
        REMAINING("remaining"),
        EXIT("exit");

        String label;

        Action (String s) {
            this.label = s;
        }

        public static Action getAction(String s) {
            for (Action a : Action.values()) {
                if (a.label.equals(s)) {
                    return a;
                }
            }
            return Action.EXIT;
        }
    }

    enum Coffee {
        ESPRESSO("1"),
        LATTE("2"),
        CAPPUCCINO("3"),
        BACK("back");

        String label;

        Coffee(String label) {
            this.label = label;
        }

        public static Coffee getCoffee(String s) {
            for (Coffee coffee : Coffee.values()) {
                if (coffee.label.equals(s)) {
                    return coffee;
                }
            }
            return Coffee.BACK;
        }
    }

    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        boolean continueAsking;
        do {
            continueAsking = coffeeMachine.ProcessAction();
        }
        while (continueAsking);
    }

    public boolean ProcessAction() {
        System.out.println("Write action (buy, fill, take, remaining, exit): ");
        Action action =  Action.getAction(this.scanner.next());
        switch (action) {
            case BUY:
                this.processBuy();
                break;
            case FILL:
                this.processFill();
                break;
            case TAKE:
                this.processTake();
                break;
            case REMAINING:
                System.out.println(this.getState());
                break;
            case EXIT:
                return false;
            default:
                System.out.println("Unknown action");
                break;
        }
        return true;
    }

    public void processBuy() {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino: ");
        Coffee coffee = Coffee.getCoffee(this.scanner.next());
        switch (coffee) {
            case ESPRESSO:
                this.buyEspresso();
                break;
            case LATTE:
                this.buyLatte();
                break;
            case CAPPUCCINO:
                this.buyCappuccino();
                break;
            case BACK:
                break;
        }
    }

    public void processFill() {
        System.out.println("Write how many ml of water do you want to add: ");
        int fillWater = this.scanner.nextInt();
        System.out.println("Write how many ml of milk do you want to add: ");
        int fillMilk = this.scanner.nextInt();
        System.out.println("Write how many grams of coffee beans do you want to add: ");
        int fillCoffee = this.scanner.nextInt();
        System.out.println("Write how many disposable cups of coffee do you want to add: ");
        int fillCups = this.scanner.nextInt();
        this.fill(fillWater, fillMilk, fillCoffee, fillCups);
    }

    public void processTake() {
        int moneyAvailable = getMoney();
        adjustMoney(-moneyAvailable);
        System.out.println(String.format("I gave you $%d", moneyAvailable));;
    }
}
