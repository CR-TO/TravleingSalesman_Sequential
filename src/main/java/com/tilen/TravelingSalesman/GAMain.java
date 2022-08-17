package com.tilen.TravelingSalesman;

import com.tilen.Engine.Game;
import com.tilen.Engine.Utils.DrawUtils;
import com.tilen.Engine.Vector2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static com.tilen.Engine.Utils.ListUtils.*;
import static com.tilen.Engine.Utils.MathUtils.*;
import static com.tilen.Engine.Utils.StringUtils.numberFormat;
import static com.tilen.TravelingSalesman.TravelingSalesman.HEIGHT;
import static com.tilen.TravelingSalesman.TravelingSalesman.WIDTH;

public class GAMain {

    public long MAX_GENERATIONS;

    public int GEN = 0;
    public long recordDistance = Long.MAX_VALUE;
    public long curWork = 0;

    public int[] bestEver;
    public int[] currentBest;

    // Array list of orders (which are Array lists of integers)!
    ArrayList<ArrayList<Integer>> population = new ArrayList<>();
    ArrayList<Float> fitness = new ArrayList<>();
    ArrayList<Vector2D> cities = new ArrayList<>();

    int totalCities;

    int popSize;
    int nodeSize = 16;

    TravelingSalesman ts;


    public float maxWork;

    public GAMain(TravelingSalesman ts, int totalCities, int popSize) {
        this.totalCities = totalCities;
        this.popSize = popSize;
        this.ts = ts;
        this.maxWork = Float.MAX_VALUE;
        this.MAX_GENERATIONS = factorial(totalCities);
        if(this.MAX_GENERATIONS < 0) {
            this.MAX_GENERATIONS = Long.MAX_VALUE;
        }
    }

    public void setup() {
        ArrayList<Integer> order = new ArrayList<>();
        for (int i = 0; i < totalCities; i++) {
            int xOff = 50;
            int yOff = 600;
            double x = random((Game.WIDTH - xOff) / 2f);
            double y = yOff / 8f + random(((Game.HEIGHT - yOff / 2f)));
            cities.add(new Vector2D(x, y));
            order.add(i);
        }

        for (int i = 0; i < this.popSize; i++) {
            this.population.add(cloneList(order));
            shuffle(this.population.get(i));
        }
    }

    public void run() {
        this.curWork = this.curWork + 1;
        calcFitness();
        normalizeFitness();
        nextGeneration();
    }

    public void calcFitness() {
        long curRecord = Long.MAX_VALUE;
        for (int i = 0; i < this.population.size(); i++) {
            long d = calcDistance(cities, population.get(i));
            if(d < this.recordDistance) {
                this.recordDistance = d;
                this.bestEver = toIntArray(population.get(i));
            }
            if(d < curRecord) {
                curRecord = d;
                this.currentBest = toIntArray(population.get(i));
            }

            float dd = d + 1;
            float f = 1 / dd;
            fitness.add(f);
        }
    }

    public void normalizeFitness() {
        float maxFit = 0;
        for (int i = 0; i < fitness.size(); i++) {
            int n = i % fitness.size();
            if(fitness.get(n) != null) {
                maxFit += fitness.get(n);
            }
        }
        for (int i = 0; i < fitness.size(); i++) {
            int n = i % fitness.size();
            if(fitness.get(n) != null) {
                fitness.set(n, fitness.get(n) / maxFit);
            }
        }
    }

    public void nextGeneration() {
        ArrayList<ArrayList<Integer>> newPop = new ArrayList<>();
        for (int i = 0; i < population.size(); i++) {

            ArrayList<Integer> orderOne = pickRand(population, fitness);
            ArrayList<Integer> orderTwo = pickRand(population, fitness);
            ArrayList<Integer> order = crossover(orderOne, orderTwo);
            mutation(order);
            newPop.add(order);
        }
        population = newPop;
    }

    public void mutation(ArrayList<Integer> order) {
        switch (new Random().nextInt(5)) {
            case 0:
                mutate_insertion(order);
                break;
            case 1:
                mutate_insertion(order);
                break;
            case 2:
                mutate_branch_swap(order);
                break;
            case 3:
                mutate_branch_swap(order);
                break;
            case 4:
                mutate(order);
                break;
        }
    }

    public void mutate(ArrayList<Integer> order) {

        int randomIndexStart = (int)random(order.size());
        int randomIndexEnd = (int)random(order.size());

        for (int i = randomIndexStart; i%order.size() != randomIndexEnd; i++) {
            int r = (int)random(Math.abs(i%order.size() - randomIndexEnd));
            swap(order, i%order.size(), (i+r)%order.size());
        }
    }

    public ArrayList<Integer> crossover(ArrayList<Integer> oa, ArrayList<Integer> ob) {
        int start = floor((int) random(oa.size()));
        int end = floor((int) random(start + 1, oa.size()));
        ArrayList<Integer> newOrder = new ArrayList<>();
        for (int i = start; i < end; i++){
            newOrder.add(oa.get(i));
        }

        for (int i = 0; i < ob.size(); i++) {
            int city = ob.get(i);
            if (!newOrder.contains(city)) {
                newOrder.add(city);
            }
        }
        return newOrder;
    }

    public void mutate_insertion(ArrayList<Integer> order) {
        Random r = new Random();
        int element = r.nextInt(order.size()-2)+1;
        int insert_after = r.nextInt(order.size()-2)+1;

        if(element != insert_after) {
            int element_copy = order.get(element);

            ArrayList<Integer> removed = new ArrayList<>();

            for (int i = 0; i < order.size() - 1; i++) {
                removed.add(-1);
            }

            for(int i=0; i<element;i++){
//                removed[i] = order[i];

                removed.set(i, order.get(i));
            }

            for(int i=element; i< order.size()-1; i++){
                removed.set(i, order.get(i+1));
            }

            //create an array with element inserted
            for(int i=1; i<insert_after;i++){
                order.set(i, removed.get(i));
            }
            order.set(insert_after, element_copy);

            for(int i=insert_after+1; i<order.size()-1; i++){
                order.set(i, removed.get(i-1));
            }
        }
//        calcDistance(this.cities, order);
    }

    public void mutate_branch_swap(ArrayList<Integer> order){
        Random r = new Random();

        int swap1 = r.nextInt(order.size());
        int swap2 = r.nextInt(order.size());

        int start = Math.min(swap1, swap2);
        int stop =  Math.max(swap1, swap2);

        if(stop-start > 1) {
            ArrayList<Integer> swapper = new ArrayList<>();
            for (int i = 0; i < stop-start+1; i++) {
                swapper.add(-1);
            }
            int swapper_count = 0;
            for(int i=stop; i>(start-1); i--) {
                swapper.set(swapper_count, order.get(i));
                swapper_count++;
            }

            swapper_count = 0;
            for(int i=start; i<(stop+1); i++) {
                order.set(i, swapper.get(swapper_count));
                swapper_count++;
            }
        }
    }

    public void draw(Game game, Graphics2D g) {
        run();

        DrawUtils du = new DrawUtils(g);

        drawData(du);
        du.translate(WIDTH / 4, 0);
        drawBestPath(du);
        drawPermutations(new DrawUtils((Graphics2D) g.create()));
    }

    public void drawData(DrawUtils du) {

        int x = WIDTH / 2;

        du.setFontSize(20);

        du.textCentered("Record: " + numberFormat( 8, Math.sqrt(recordDistance)), x, 5);
        du.textCentered("Generation:" , x, HEIGHT - 95);
        du.textCentered(GEN, x, HEIGHT - 75);
    }

    public void drawBestPath(DrawUtils du) {
        if (bestEver == null) return;
        du.fill(0, 255, 0);
        for (int i = 1; i < bestEver.length; i++) {
            int po = bestEver[i - 1];
            int co = bestEver[i];

            Vector2D pc = cities.get(po);
            Vector2D cc = cities.get(co);

            du.storke(3);
            du.line(cc.x + this.nodeSize / 2f, cc.y + this.nodeSize / 2f, pc.x + this.nodeSize / 2f, pc.y + this.nodeSize / 2f);
        }

        drawCities(du);
    }

    public void drawCities(DrawUtils du) {
        du.fill(255);

        for (int i = 0; i < bestEver.length; i++) {
            int n = bestEver[i];
            du.ellipse(cities.get(n).x, cities.get(n).y, this.nodeSize, this.nodeSize);
        }
    }

    public void drawPermutations(DrawUtils du) {
        if (currentBest == null) return;

        du.translate(0, 0);
        du.fill(255,255,255,75);
        for (int i = 1; i < currentBest.length; i++) {
            int po = currentBest[i - 1];
            int co = currentBest[i];

            Vector2D pc = cities.get(po);
            Vector2D cc = cities.get(co);

            du.storke(0.25f);
            du.line(cc.x + this.nodeSize / 2f, cc.y + this.nodeSize / 2f, pc.x + this.nodeSize / 2f, pc.y + this.nodeSize / 2f);
        }

//        drawCities(du);
        du.pop();
    }
}
