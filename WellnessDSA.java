import java.util.*;

// HealthRecord class represents a single health entry
// CO5: Practical application of data structures (Health Monitoring System)

class HealthRecord {

    int sleep;
    double water;
    int steps;
    String stress;
    int score;

    HealthRecord(int sleep,double water,int steps,String stress,int score){
        this.sleep=sleep;
        this.water=water;
        this.steps=steps;
        this.stress=stress;
        this.score=score;
    }

    // Display record information
    public String toString(){
        return "Sleep:"+sleep+"h | Water:"+water+"L | Steps:"+steps+
               " | Stress:"+stress+" | Score:"+score;
    }
}

public class WellnessDSA {

    // Scanner used for user input
    static Scanner sc = new Scanner(System.in);

    // CO2: ArrayList used as Abstract Data Type (List ADT)
    // Used to store multiple health records
    static ArrayList<HealthRecord> records = new ArrayList<>();

    // CO3: Stack data structure used for Undo operation
    static Stack<HealthRecord> undoStack = new Stack<>();

    // CO3: Queue used for processing daily records
    static Queue<HealthRecord> dailyQueue = new LinkedList<>();

    // CO4: HashMap used for storing stress statistics
    static HashMap<String,Integer> stressStats = new HashMap<>();


    public static void main(String[] args) {

        int choice;

        do{

            // Menu driven system
            // CO6: Complete program development using data structures
            System.out.println("\n===== WELLNESS HEALTH SYSTEM =====");
            System.out.println("1 Add Health Record");
            System.out.println("2 Display Records");
            System.out.println("3 Analyze New Health Input");
            System.out.println("4 Sort Records by Steps (Bubble Sort)");
            System.out.println("5 Search Record by Steps (Binary Search)");
            System.out.println("6 Undo Last Record (Stack)");
            System.out.println("7 Process Daily Queue");
            System.out.println("8 Stress Statistics");
            System.out.println("9 Exit");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch(choice){

                case 1: addRecord(); break;
                case 2: displayRecords(); break;
                case 3: analyzeHealthInput(); break;
                case 4: bubbleSort(); break;
                case 5: binarySearch(); break;
                case 6: undo(); break;
                case 7: processQueue(); break;
                case 8: showStressStats(); break;
                case 9: System.out.println("Exiting System..."); break;

                default: System.out.println("Invalid choice");
            }

        }while(choice!=9);
    }


    // Health score calculation algorithm
    // CO1: Algorithm design and evaluation

    static int calculateHealthScore(int sleep,double water,int steps,String stress){

        int score=0;

        if(sleep>=7 && sleep<=9)
            score+=30;

        if(water>=2)
            score+=30;

        if(steps>=8000)
            score+=20;

        if(stress.equalsIgnoreCase("Low"))
            score+=20;

        return score;
    }


    // Add new record
    // CO2: Insert operation in ArrayList
    static void addRecord(){

        System.out.print("Enter sleep hours: ");
        int sleep=sc.nextInt();

        System.out.print("Enter water intake (liters): ");
        double water=sc.nextDouble();

        System.out.print("Enter steps walked: ");
        int steps=sc.nextInt();

        System.out.print("Enter stress level (Low/Medium/High): ");
        String stress=sc.next();

        int score = calculateHealthScore(sleep,water,steps,stress);

        HealthRecord record = new HealthRecord(sleep,water,steps,stress,score);

        // Adding record to list
        records.add(record);

        // CO3: Push into stack for undo feature
        undoStack.push(record);

        // CO3: Add into queue for daily processing
        dailyQueue.add(record);

        // CO4: Store stress statistics using HashMap
        stressStats.put(stress,stressStats.getOrDefault(stress,0)+1);

        System.out.println("\nHealth Score: "+score+"/100");

        if(score>=80)
            System.out.println("Excellent Health");
        else if(score>=60)
            System.out.println("Good but can improve");
        else
            System.out.println("Health needs improvement");
    }


    // Traverse records
    // CO2: Traversal operation on List
    static void displayRecords(){

        if(records.isEmpty()){
            System.out.println("No records available.");
            return;
        }

        for(HealthRecord r:records)
            System.out.println(r);
    }


    // Bubble Sort Algorithm
    // CO1: Classical sorting algorithm
    // Time Complexity: O(n²)

    static void bubbleSort(){

        for(int i=0;i<records.size()-1;i++){

            for(int j=0;j<records.size()-i-1;j++){

                if(records.get(j).steps > records.get(j+1).steps){

                    HealthRecord temp = records.get(j);
                    records.set(j,records.get(j+1));
                    records.set(j+1,temp);
                }
            }
        }

        System.out.println("Records sorted by steps.");
        displayRecords();
    }


    // Binary Search Algorithm
    // CO1: Searching algorithm
    // Time Complexity: O(log n)

    static void binarySearch(){

        System.out.print("Enter steps to search: ");
        int target=sc.nextInt();

        bubbleSort(); // Binary search requires sorted list

        int left=0;
        int right=records.size()-1;

        while(left<=right){

            int mid=(left+right)/2;

            if(records.get(mid).steps==target){

                System.out.println("Record Found:");
                System.out.println(records.get(mid));
                return;
            }

            else if(records.get(mid).steps<target)
                left=mid+1;

            else
                right=mid-1;
        }

        System.out.println("Record not found.");
    }


    // Undo last record
    // CO3: Stack data structure application

    static void undo(){

        if(undoStack.isEmpty()){
            System.out.println("Nothing to undo.");
            return;
        }

        HealthRecord removed = undoStack.pop();
        records.remove(removed);

        System.out.println("Last record removed.");
    }


    // Queue processing
    // CO3: Queue data structure application

    static void processQueue(){

        if(dailyQueue.isEmpty()){
            System.out.println("Queue empty.");
            return;
        }

        System.out.println("Processing Daily Records:");

        while(!dailyQueue.isEmpty())
            System.out.println(dailyQueue.poll());
    }


    // HashMap usage
    // CO4: Hash based data structure

    static void showStressStats(){

        System.out.println("Stress Level Statistics:");

        for(String s:stressStats.keySet())
            System.out.println(s+" : "+stressStats.get(s));
    }


    // Direct health analysis
    // CO5: Practical real world application

    static void analyzeHealthInput(){

        System.out.print("Sleep hours: ");
        int sleep=sc.nextInt();

        System.out.print("Water intake (L): ");
        double water=sc.nextDouble();

        System.out.print("Steps: ");
        int steps=sc.nextInt();

        System.out.print("Stress: ");
        String stress=sc.next();

        int score=calculateHealthScore(sleep,water,steps,stress);

        System.out.println("\nHealth Score: "+score+"/100");

        if(score>=80)
            System.out.println("Excellent lifestyle");
        else if(score>=60)
            System.out.println("Good but improve more");
        else
            System.out.println("Unhealthy lifestyle");
    }
}