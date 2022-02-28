package com.hataysoftware;

import java.awt.*;
import java.util.*;

public class Main {

    public static ArrayList list = new ArrayList();

    public static int active_player = 1;
    public static int wait_playercount = 0;
    public static int loser_playercount = 0;
    public static ArrayList listPlayer = new ArrayList();
    public static long sleep_time = 100;
    public  static String player_name="Oyuncu";


    public static void main(String[] args) throws InterruptedException {
        // write your code here


        list = Liste();

        listPlayer.add(new player("PC1"));
        listPlayer.add(new player("PC2"));
        listPlayer.add(new player("PC3"));
        listPlayer.add(new player(player_name));
        active_player = listPlayer.size();
        int start = 0;
        for (int i = 0; i < list.size(); ) {

            for (int j = 0; j < active_player; j++) {

                if (start < active_player * 2) {
                    player player_ = (player) listPlayer.get(j);
                    player_ = player_next(player_, false);
                    listPlayer.set(j, player_);
                    start++;
                } else if (startProgram(j)) break;

            }
            if (wait_playercount == active_player || loser_playercount == active_player - 1)
                break;
        }
        int winner = winnerNumber();
        allWinner(winner);
    }

    public static void durum(int sira, player player) throws InterruptedException {

        if (player.wait && !player.lose)
            System.out.println((sira + 1) + " .Player Bekliyor.");
        else if (!player.wait) {
            System.out.println((sira + 1) + " .Player Oynuyor.");
        }
        Thread.sleep(sleep_time);
    }

    public static boolean startProgram(int index) throws InterruptedException {
        player player_ = (player) listPlayer.get(index);
        durum(index, player_);
        if (player_.player_name.equals(player_name)) {
            if (!player_.wait && !player_.win && !player_.lose) {
                System.out.println("Kartlarınız: ");
                getCartsPlayer(player_);
                System.out.println("Kart çek = 1 \n Bekle = 2");
                Scanner scanner = new Scanner(System.in);
                boolean swich_ = false;
                while (!swich_) {
                    optimizasyon();
                    switch (scanner.nextInt()) {
                        case 1:
                            player_ = player_next(player_, true);
                            System.out.println("Yeni Kartlarınız: ");
                            listPlayer.set(index, player_);
                            getCartsPlayer(player_);
                            swich_ = true;
                            break;
                        case 2:
                            player_.wait = true;
                            wait_playercount++;
                            listPlayer.set(index, player_);
                            swich_ = true;
                            break;
                    }
                    if (!swich_) System.out.println("Kart çek = 1 \nBekle = 2");
                    optimizasyon();
                }
            }
        } else {
            player_ = player_next(player_, false);
            listPlayer.set(index, player_);
        }
        return wait_playercount == active_player || loser_playercount == active_player - 1;
    }

    public static void getCartsPlayer(player player_) {
        String carts = "";
        for (int j = 0; j < player_.carts.size(); j++)
            carts += player_.carts.get(j).color + player_.carts.get(j).symbol + player_.carts.get(j).type + " ";
        System.out.println(player_.player_name + " kartları: " + carts);
        colors.changeColorText("", "");
    }

    public static void allWinner(int winner_point) {
        ArrayList list = scoreList();
        int count = 0;
        for (int i = 0; i < list.size(); i++) {

            player_score score = ((player_score) list.get(i));

            if (winner_point == score.puan) {
                System.out.println("Kazanan oyuncu " + score.player.player_name + " puanı: " + score.puan);
                count++;
            }
        }
        if (count > 1) {
            System.out.println("Beraberlik");
        }
    }

    public static int winnerNumber() {
        ArrayList scores = scoreList();
        int winner = 0;
        for (int i = 0; i < scores.size(); i++) {
            player_score score = ((player_score) scores.get(i));
            player_score score_w = ((player_score) scores.get(winner));

            if (21 - score.puan < 21 - score_w.puan)
                winner = i;
            System.out.print(score.player.player_name + " oyuncu puan " + score.puan + " kartlar: ");
            for (int j = 0; j < score.player.carts.size(); j++) {
                cart cart = score.player.carts.get(j);
                System.out.print(cart.color + cart.symbol + cart.type + " ");
                colors.changeColorText("", "");
            }
            System.out.println(" ");
        }
        return ((player_score) scores.get(winner)).puan;
    }

    public static ArrayList scoreList() {
        ArrayList scores = new ArrayList();
        for (int i = 0; i < active_player; i++) {
            player player_ = (player) listPlayer.get(i);
            if (player_.lose)
                scores.add(new player_score(player_, 0));
            else if (player_.win)
                scores.add(new player_score(player_, 21));
            else {
                int count = player_.puans.size();
                int big = 0;
                for (int j = 0; j < count; j++) {
                    risk item = (risk) player_.puans.get(j);
                    risk item2 = (risk) player_.puans.get(big);
                    if (item.puan > item2.puan)
                        big = j;
                }

                scores.add(new player_score(player_, ((risk) player_.puans.get(big)).puan));


            }

        }
        return scores;
    }

    public static player player_next(player player1, boolean player_active) {
        if (player1.play && !player1.wait) {
            Random random = new Random();
            int randomCart = random.nextInt(list.size());

            if (!player_active) {
                player1 = continue_cart(player1);
                if (!player1.wait)
                    player1 = playerCartAdd((cart) list.get(randomCart), player1);
                else{
                    System.out.println("Bekliyor.");
                }
            }
            if (player_active) {
                player1 = playerCartAdd((cart) list.get(randomCart), player1);
            }

        }
        return player1;
    }

    public static player continue_cart(player player) {
        if (!player.wait) {
            int total = 0;
            int count = 0;
            for (int i = 0; i < player.puans.size(); i++) {
                var item = (risk) player.puans.get(i);
                if (item.risk > 80) {
                    wait_playercount++;
                    player.wait = true;

                    break;
                } else {
                    total += item.risk;
                    count++;
                }
            }
            if (total > 0) total /= count;
            if (!player.wait) {
                Random random = new Random();
                int random_n = (random.nextInt(500000) % 100) + 1;
                if (random_n > total)
                    player.wait = false;

                else {
                    player.wait = true;
                    wait_playercount++;
                }
            }
        }
        return player;

    }

    public static player playerCartAdd(cart cart, player player) {

        player.carts.add(cart);
        //  System.out.println(cart.symbol + cart.type);
        if (specialCards(cart)) {
            player = specialCart(player, cart);
        } else {
            int count = player.puans.size();
            for (int i = 0; i < count; i++)
                player.addPuan(i, cart.puan);
        }
        player = playerControl(player);
        list.remove(cart);
        return player;
    }

    public static boolean specialCards(cart cart) {
        if( cart.type.equals("A"))
            return true;
        return false;

    }

    public static void optimizasyon() {
        wait_playercount = 0;
        for (int i = 0; i < active_player; i++) {
            var player = (player) listPlayer.get(i);
            if (player.wait)
                wait_playercount++;
        }
    }
    public static player playerControl(player player) {

        int count = player.puans.size();
        for (int i = 0; i < count; i++) {
            int _puan = ((risk) player.puans.get(i)).puan;
            if (_puan > 21) {
                player.puans.remove(i);
                i--;
                count--;
                if (count == 0) {
                    System.out.println();
                    colors.changeColorText(colors.ANSI_PURPLE, player.player_name + " Kaybetti 21'i aştı\n puanı:" + _puan);
                    player.lose = true;
                    player.wait = true;
                    wait_playercount++;
                    loser_playercount++;
                }
            } else if (_puan == 21) {
                player.puans.clear();
                player.newPuan(21);
                player.win = true;
                player.wait = true;
                wait_playercount++;
                break;
            }

        }
        return player;
    }

    public static player specialCart(player player, cart cart) {

        int count = player.puans.size();
        if (cart.type.equals("A"))
            for (int i = 0; i < count; i++) {
                int puan_temp = ((risk) player.puans.get(i)).puan;
                player.addPuan(i, 1);
                player.newPuan(puan_temp + 11);
            }


        return player;
    }

    public static ArrayList type_new(ArrayList typeList, String symbol, String renk) {
        String[] t = new String[2];
        t[0] = symbol;
        t[1] = renk.toLowerCase(Locale.ROOT);
        typeList.add(t);
        return typeList;
    }

    public static ArrayList Liste() {
        ArrayList list = new ArrayList();

        list.add(new cart("A", "", 11, ""));
        list.add(new cart("2", "", 2, ""));
        list.add(new cart("3", "", 3, ""));
        list.add(new cart("4", "", 4, ""));
        list.add(new cart("5", "", 5, ""));
        list.add(new cart("6", "", 6, ""));
        list.add(new cart("7", "", 7, ""));
        list.add(new cart("8", "", 8, ""));
        list.add(new cart("9", "", 9, ""));
        list.add(new cart("10", "", 10, ""));
        list.add(new cart("J", "", 10, ""));
        list.add(new cart("Q", "", 10, ""));
        list.add(new cart("K", "", 10, ""));

        ArrayList typeList = new ArrayList();

        typeList = type_new(typeList, "♣", "black");
        typeList = type_new(typeList, "♥", "red");
        typeList = type_new(typeList, "♦", "blue");
        typeList = type_new(typeList, "♠", "green");

        ArrayList total = new ArrayList();
        for (var typeItem : typeList) {
            String[] a = (String[]) typeItem;
            for (var item : list) {
                cart _cart = ((cart) item);
                total.add(new cart(_cart.type, a[0], _cart.puan, a[1]));

            }

        }
        return total;
    }
}
