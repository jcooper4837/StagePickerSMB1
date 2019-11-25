import java.util.*;
import java.io.*;

public class TourneyPickerSMB1 
{
    public static void main(String[] args) 
    {
        int size;
        if (args.length == 0)
        {
            size = 5;
        }
        else
        {
            size = Integer.parseInt(args[0]);
        }
	boolean cont = true;
	System.out.println("Welcome to Tourney Picker\n");
	Scanner sc = new Scanner(System.in);
        
        File file = new File("smb1stages.txt");
        String line;
        ArrayList<String> stages = new ArrayList<>(0);
        ArrayList<String> used = new ArrayList<>(0);
        ArrayList<String> played = new ArrayList<>(0);
        ArrayList<Integer> stageNums = new ArrayList<>(0);
        ArrayList<String> current;
        int length = 118;
        int currentSize = length;
        String[] pool = new String[size];
        int stPick;
        String pick;
        int of;
        
        File file2 = new File("prob.txt");
        Integer line2;
        ArrayList<Integer> prob = new ArrayList<>();
        int[] probPool = new int[length];
        
        try 
        { //Puts file of stages into arraylist stages
            FileReader fr = new FileReader(file);
            //int sdk = 0;
            try (BufferedReader br = new BufferedReader(fr)) 
            {
                //int sdk = 0;
                while((line = br.readLine()) != null)
                {
                    //if (sdk < 20)
                    stages.add(line);
                    //sdk++;
                    //System.out.println(line);
                }
            }
        }
        catch(FileNotFoundException e) 
        {
            System.out.println("smb1stages.txt not found");
	}
        catch(IOException e) 
        {
            System.out.println("exception found for prob");
	}
        catch(IllegalArgumentException e) 
        { //args[0] is amount of stages to print, must be integer
            System.out.println("Argument must be an integer");
        }
        
        try 
        { //Puts file of stages into arraylist stages
            FileReader fr2 = new FileReader(file2);
            try (BufferedReader br2 = new BufferedReader(fr2)) 
            {
                while((line = br2.readLine()) != null)
                {
                    if (line.length() > 0)
                    {
                        line2 = Integer.parseInt(line);
                        probPool[line2 - 1]--;
                        //System.out.println(stages.get(line2 - 1));
                    }
                }
            }
        }
        catch(FileNotFoundException e) 
        {
            System.out.println("prob.txt not found");
	}
        catch(IOException e) 
        {
            System.out.println("exception found for prob");
	}
        catch(IllegalArgumentException e) 
        { //args[0] is amount of stages to print, must be integer
            System.out.println("Argument must be an integer 2");
        }
        
        for (int i = 0; i < length; i++)
        {
            if ((i+1) == 5 || (i+1) == 15 || (i+1) == 20 || (i+1) == 30 || (i+1) == 45 || (i+1) == 50 || (i+1) == 60 || (i+1) == 70 || (i+1) == 104 || (i+1) == 107 || (i+1) == 109 || (i+1) == 111 || (i+1) == 116)
            {
                probPool[i] = -1;
            }
            else
            {
                probPool[i] += 2;
            }
        }
        
        for (int i = 0; i < length; i++)
        {
            //System.out.println(probPool[i] + "   " + stages.get(i));
        }
        
        int c = 0;
        for (int i = 0; i < length; i++)
        {
            for (int j = 0; j < length; j++)
            {
                if (probPool[j] == i)
                {
                    //System.out.println(probPool[j] + "   " + stages.get(j));
                    c += i;
                }
            }
        }
        //System.out.println(c);
        
	while(cont) 
        {
            //System.out.println(used);
            of = 0;
            if (used.size() > length - size)
            {
                used.clear();
            }
            current = new ArrayList<>(0);
			
            System.out.println("\nHere are your " + size + " stages\n");

            for(int i = 0; i < size; i++) 
            {
                //System.out.println("of = " + of);
                if (of > 10000)
                {
                    cont = false;
                    break;
                }
                of++;
                //System.out.println("currentSize = " + currentSize);
                int num = (int)(Math.random() * length);
                //System.out.println("num = " + num);
                String st = stages.get(num);
                //System.out.println(stages.size() + " " + used.size());
                if(!used.contains(st) && !current.contains(st) && probPool[num] == 0) 
                { //if stage was not already picked and not in current set
                    System.out.println((i + 1) + ": " + st);
                    current.add(st);
                    used.add(st);
                    stageNums.add(num + 1);
                    pool[i] = st;
                    //currentSize--;
		}
                else if (probPool[num] != 0)
                {
                    //System.out.println(probPool[num] + "          : " + st);
                    if (probPool[num] > 0)
                    {
                        probPool[num]--;
                    }
                    if (stages.size() - used.size() > size || stages.size() - used.size() > i)
                    {
                        i--;
                    }
                }
		else 
                {
                    //System.out.println("if = " + (stages.size() - used.size()) + "\nsize = " + size + "\ni = " + i);
                    //if (stages.size() - used.size() > size || stages.size() - used.size() > i)
                    {
                        i--;
                    }
		}
            }
		
            System.out.println("-1: Reshuffle\n0: Exit");
            current.clear(); //clear current set
		
            boolean reshuffle = true;
            boolean isValid = false;
            while (!isValid)
            {
                System.out.print("\nPick a stage (enter the number): ");
                stPick = sc.nextInt();
                    
                if (stPick > 0 && stPick <= size)
                {
                    pick = pool[stPick - 1];
                    played.add(pick); //adds to the list of already picked stages
                    
                    for (int j = 0; j < pool.length; j++)
                    {
                        //used.add(pool[j]);
                    }
                    isValid = true;
                }
                else if (stPick == 0)
                {
                    int listSize = used.size();
                    for (int i = listSize - 1; i > listSize - 1 - size; i--)
                    {
                        //System.out.println((listSize - 1) + "   " + (listSize - 1 - size));
                        used.remove(i);
                        stageNums.remove(i);
                    }
                    isValid = true;
                    cont = false;
                }
                else if (stPick < 0)
                {
                    isValid = true;
                }
            }
	}
        /*System.out.println("\nAdd this list to prob.txt: ");
        for (int i = 0; i < stageNums.size(); i++)
        {
            System.out.println(stageNums.get(i));
        }*/
        System.out.println("\nPlayed stages: ");
        for (int i = 0; i < played.size(); i++)
        {
            System.out.println(played.get(i));
        }
    }
}