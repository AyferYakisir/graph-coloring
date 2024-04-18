package com.example.graphcoloring;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class GrafBoyama {
    public static Map<Integer, Integer> acgozluBoyama(Map<Integer, List<Integer>> graf) {
        Map<Integer, Integer> renkler = new HashMap<>();
        for (int dugum : graf.keySet()) {
            Set<Integer> komsuRenkler = new HashSet<>();
            for (int komsu : graf.get(dugum)) {
                if (renkler.containsKey(komsu)) {
                    komsuRenkler.add(renkler.get(komsu));
                }
            }
            int renk = 1;
            while (komsuRenkler.contains(renk)) {
                renk++;
            }
            renkler.put(dugum, renk);
        }
        return renkler;
    }

    public static Map<Integer, List<Integer>> grafOlustur(List<int[]> kenarlar) {
        Map<Integer, List<Integer>> graf = new HashMap<>();
        for (int[] kenar : kenarlar) {
            int u = kenar[0];
            int v = kenar[1];
            graf.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
            graf.computeIfAbsent(v, k -> new ArrayList<>()).add(u);
        }
        return graf;
    }

    public static void main(String[] args) {
        String klasorYolu = "C:\\Users\\Ayfer\\OneDrive\\Masaüstü\\graph coloring\\data\\";
        String[] dosyaIsimleri = {"gc_20_1", "gc_50_7", "gc_70_1", "gc_500_3", "gc_1000_9"};

        for (String dosyaIsim : dosyaIsimleri) {
            String dosyaYolu = klasorYolu + dosyaIsim + ".txt";
            try {
                File file = new File(dosyaYolu);
                Scanner scanner = new Scanner(file);
                int V = scanner.nextInt(); // Düğüm sayısı
                int E = scanner.nextInt(); // Kenar sayısı
                List<int[]> kenarlar = new ArrayList<>();
                for (int j = 0; j < E; j++) {
                    int u = scanner.nextInt();
                    int v = scanner.nextInt();
                    kenarlar.add(new int[]{u, v});
                }
                Map<Integer, List<Integer>> graf = grafOlustur(kenarlar);
                Map<Integer, Integer> boyama = acgozluBoyama(graf);
                int farkliRenkSayisi = new HashSet<>(boyama.values()).size();
                
                // Çıktı dosyasına yazma
                String ciktiDosyaYolu = klasorYolu + dosyaIsim + "_cikti.txt";
                PrintWriter writer = new PrintWriter(ciktiDosyaYolu);
                writer.println("Kullanılan farklı renk sayısı: " + farkliRenkSayisi);
                writer.println("Grafiğin renklendirilmesi:");
                for (Map.Entry<Integer, Integer> entry : boyama.entrySet()) {
                    writer.println("Köşe " + entry.getKey() + " ----> Renk " + entry.getValue());
                }
                writer.close();
                
                System.out.println("Dosya " + dosyaIsim + " için çıktı oluşturuldu: " + ciktiDosyaYolu);
            } catch (FileNotFoundException e) {
                System.err.println("Dosya bulunamadı: " + dosyaYolu);
            }
        }
    }
}


