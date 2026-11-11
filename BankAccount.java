public class BankAccount {
    private String name;
    private int money;
    private static int accountCount = 0;

    BankAccount(String name, int money) {
        this.name = name;
        setMoney(money);
        accountCount++;
    }

    private void setMoney(int money) {
        if (money < 0) {
            this.money = 0;
        } else {
            this.money = money;
        }
    }

    void print() {
        System.out.printf("Owner: %s | Balance: %d%n", name, money);
    }

    // Пополнение
    public void deposit(int dep) {
        if (dep <= 0) {
            System.out.println("Сумма пополнения должна быть больше 0!");
            return;
        }
        money += dep;
        System.out.printf("Счёт '%s' пополнен на %d. Текущий баланс: %d%n", name, dep, money);
    }

    // Снятие
    public void cash(int dep) {
        if (dep <= 0) {
            System.out.println("Сумма снятия должна быть больше 0!");
            return;
        }
        if (dep > money) {
            System.out.println("На счёте недостаточно средств!");
            return;
        }
        money -= dep;
        System.out.printf("Со счёта '%s' снято %d. Текущий баланс: %d%n", name, dep, money);
    }

    // Перевод
    public void transfer(BankAccount target, int dep) {
        if (dep <= 0) {
            System.out.println("Вы не можете перевести 0 или меньше!");
            return;
        }
        if (dep > money) {
            System.out.println("На вашем счёте недостаточно средств!");
            return;
        }
        money -= dep;
        target.deposit(dep);
        System.out.printf("Перевод %d выполнен: '%s' -> '%s'%n", dep, name, target.name);
    }

    public String getName() {
        return this.name;
    }

    public int getMoney() {
        return this.money;
    }

    public static int getAccountCount() {
        return accountCount;
    }
}