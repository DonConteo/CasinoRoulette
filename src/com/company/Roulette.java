package com.company;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Roulette {
    private int cash = 1000;

    public static void main(String[] args) {
        Roulette roll = new Roulette();
        roll.subMain();
    }

    public void subMain(){
        System.out.println("Приветствуем вас в нашем казино!");
        haveABreak(1);
        System.out.println("Мы принимаем ставки на цвет, чет/нечет, прямые ставки.");
        haveABreak(1);
        System.out.println("При прямой ставке вы можете выбрать число от 0 до 36.");
        haveABreak(1);
        System.out.println("За один раунд можно сделать только одну ставку.");
        haveABreak(1);
        System.out.println("При прямой ставке коэффициент выигрыша составляет х35");
        haveABreak(1);
        System.out.println("При прочих ставках коэффициент выигрыша составляет х2");
        haveABreak(1);
        System.out.println("В начале игры ваш счет составляет " + cash + " $");
        haveABreak(1);
        System.out.println("Когда количество $ на вашем счете достигнет 0, вам придется покинуть казино");
        haveABreak(1);
        System.out.println("Приятной игры!");
        System.out.println(" ");
        haveABreak(2);

        do {
            int rouletteNumber = new Random().nextInt(37);
            System.out.println("Какую ставку желаете сделать? Варианты: красное, черное, чет, нечет, прямая");
            String type = new Scanner(System.in).nextLine();
            int field = -1;
            int internalCash = cash;

            if (!betType(type)) {
                System.out.println("Вы не можете сделать такую ставку. Пожалуйста, прочитайте правила казино внимательнее.");
                System.out.println(" ");
                haveABreak(1);
                continue;
            } else {
                if (type.equalsIgnoreCase("прямая")) {
                    System.out.println("На какое поле будете ставить?");
                    field = new Scanner(System.in).nextInt();

                    if (field < 0 || field > 36) {
                        System.out.println("Пожалуйста, прочитайте правила казино внимательнее.");
                        System.out.println(" ");
                        haveABreak(1);
                        continue;
                    }

                    int bet = getBet();

                    System.out.println("Выпало " + rouletteNumber);
                    System.out.println(" ");
                    if (field == rouletteNumber) win(cash, bet, 35);
                    else lose(cash, bet);
                    haveACash(cash);
                    haveABreak(2);

                } else {
                    if (type.equalsIgnoreCase("красное")) {
                        int bet = getBet();


                        if (isRed(rouletteNumber)){ win(cash, bet, 2);
                            System.out.println("Выпало " + rouletteNumber + ", красное");
                            System.out.println(" ");
                            haveABreak(2);}
                        else {lose(cash, bet);
                            System.out.println("Выпало " + rouletteNumber + ", черное");
                            System.out.println(" ");
                            haveABreak(2);}
                        haveACash(cash);
                    }

                    if (type.equalsIgnoreCase("черное")) {
                        int bet = getBet();

                        if (!(isRed(rouletteNumber) && rouletteNumber != 0)) {win(cash, bet, 2);
                            System.out.println("Выпало " + rouletteNumber + ", черное");
                            System.out.println(" ");
                            haveABreak(2);}
                        else {lose(cash, bet);
                            System.out.println("Выпало " + rouletteNumber + ", красное");
                            System.out.println(" ");
                            haveABreak(2);}
                        haveACash(cash);
                    }

                    if (type.equalsIgnoreCase("чет")) {
                        int bet1 = getBet();

                        if (isEven(rouletteNumber) && rouletteNumber != 0) {win(cash, bet1, 2);
                            System.out.println("Выпало " + rouletteNumber + ", четное");
                            System.out.println(" ");
                            haveABreak(2);}
                        else {lose(cash, bet1);
                            System.out.println("Выпало " + rouletteNumber + ", нечетное");
                            System.out.println(" ");
                            haveABreak(2);}
                        haveACash(cash);}
                }

                if (type.equalsIgnoreCase("нечет")) {
                    int bet1 = getBet();

                    if (!(isEven(rouletteNumber) && rouletteNumber != 0)) {win(cash, bet1, 2);
                        System.out.println("Выпало " + rouletteNumber + ", нечетное");
                        System.out.println(" ");
                        haveABreak(2);}
                    else {lose(cash, bet1);
                        System.out.println("Выпало " + rouletteNumber + ", четное");
                        System.out.println(" ");
                        haveABreak(2);}
                    haveACash(cash);
                }
            }
        }

        while (cash > 0);
    }

    public static void haveABreak(int period){
        try {
            TimeUnit.SECONDS.sleep(period);
        }
        catch(InterruptedException e) {
            System.out.println(e.getStackTrace());
        }
    }

    private static boolean betType(String str) {
        String[] betTypes = {"красное", "черное", "чет", "нечет", "прямая"};
        List<String> types = Arrays.asList(betTypes);

        return types.contains(str);
    }

    public static boolean isRed(int i){
        int[] red = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};

        boolean result = false;

        for(int x : red){
            if(x == i){
                result = true;
                break;
            }
        }
        return result;
    }

    public static boolean isEven(int i){
        return i % 2 == 0;
    }

    public int getBet() {
        int bet;
        do {
            System.out.println("Ваша ставка? У вас " + (getCash()) + " $");
            bet = new Scanner(System.in).nextInt();
        }
        while(!isGoodBet(bet));
        return bet;
    }

    public boolean isGoodBet(int bet){
        if (bet > cash) {
            System.out.println("Сожалеем, но вы не можете поставить больше, чем у вас есть в наличии. У вас имеется " + (getCash()) + " $");
            System.out.println(" ");
            haveABreak(1);
            return false;
        }

        if (bet == 0) {
            System.out.println("Чтобы играть, вы должны что-то поставить.");
            System.out.println(" ");
            haveABreak(1);
            return false;
        }

        return true;
    }

    public boolean haveACash(int cash) {
        if (cash == 0) {
            System.out.println("К сожалению, вы проиграли. Всего вам наилучшего! И помните - казино всегда в выгрыше.");
            return false;
        }
        return true;
    }

    public void win(int cash, int bet, int level){
        setCash(cash + bet * level);
        System.out.println("Ставка сыграла. Ваш выигрыш составил " + (bet * level) + " $");
        System.out.println("Ваша наличность = " + (getCash()) + " $");
        System.out.println(" ");
        haveABreak(1);
    }

    public void lose(int cash, int bet){
        setCash(cash - bet);
        System.out.println("Ставка не сыграла.");
        System.out.println("Ваша наличность = " + (getCash()) + " $");
        System.out.println(" ");
        haveABreak(1);
    }

    public void setCash(int i){
        this.cash = i;
    }

    public int getCash(){
        return this.cash;
    }
}
