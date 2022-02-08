package Fuzzy;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Fuzzification {

	public static double membership(double x1, double x2, double y1, double y2, double n) {
		double diffy= y2 - y1;
		double diffx= x2 - x1;
		double slope= diffy / diffx;
		double intercept= y1 - (slope*x1);
		double membership= (slope*n) + intercept;
		return membership;
	}


	public static void main(String[] args) {

		// TODO Auto-generated method stub
		ArrayList<ArrayList<Double>> all = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> vlow = new ArrayList<Double>();
		ArrayList<Double> low = new ArrayList<Double>();
		ArrayList<Double> med = new ArrayList<Double>();
		ArrayList<Double> high = new ArrayList<Double>();
		ArrayList<Double> membershipf = new ArrayList<Double>();
		
		ArrayList<ArrayList<Double>> allexp = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> beg = new ArrayList<Double>();
		ArrayList<Double> intermed = new ArrayList<Double>();
		ArrayList<Double> expert = new ArrayList<Double>();
		ArrayList<Double> membershipe = new ArrayList<Double>();
		
		ArrayList<ArrayList<Double>> allr = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> rlow = new ArrayList<Double>();
		ArrayList<Double> rnormal = new ArrayList<Double>();
		ArrayList<Double> rhigh = new ArrayList<Double>();
		ArrayList<Double> risk = new ArrayList<Double>();
		ArrayList<Double> rulesequation=new ArrayList<Double>();
		rulesequation.add(0.0);
		rulesequation.add(0.0);
		rulesequation.add(0.0);
		
		vlow.add(0.0);
		vlow.add(0.0);
		vlow.add(10.0);
		vlow.add(30.0);
		all.add(vlow);
		
		low.add(10.0);
		low.add(30.0);
		low.add(40.0);
		low.add(60.0);
		all.add(low);
		
		med.add(40.0);
		med.add(60.0);
		med.add(70.0);
		med.add(90.0);
		all.add(med);
		
		high.add(70.0);
		high.add(90.0);
		high.add(100.0);
		high.add(100.0);
		all.add(high);
		
		beg.add(0.0);
		beg.add(15.0);
		beg.add(30.0);
		allexp.add(beg);
		
		intermed.add(15.0);
		intermed.add(30.0);
		intermed.add(45.0);
		allexp.add(intermed);
		
		expert.add(30.0);
		expert.add(60.0);
		expert.add(60.0);
		allexp.add(expert);
		
		rlow.add(0.0);
		rlow.add(25.0);
		rlow.add(50.0);
		allr.add(rlow);
		
		rnormal.add(25.0);
		rnormal.add(50.0);
		rnormal.add(75.0);
		allr.add(rnormal);
		
		rhigh.add(50.0);
		rhigh.add(100.0);
		rhigh.add(100.0);
		allr.add(rhigh);
		
		ArrayList<Double> fundy = new ArrayList<Double>();
		ArrayList<Double> expy = new ArrayList<Double>();

		fundy.add(0.0);
		fundy.add(1.0);
		fundy.add(1.0);
		fundy.add(0.0);
		
		expy.add(0.0);
		expy.add(1.0);
		expy.add(0.0);
		
		System.out.println("Please, enter the project fund.");
		Scanner sc= new Scanner(System.in);
		double fund= sc.nextDouble();
		
		System.out.println("Please, enter the experience level.");
		double exp= sc.nextDouble();


		//membership of project fund

		for(int i=0;i<4;i++) {
			if(fund>=all.get(i).get(0) && fund<=all.get(i).get(3)) {
				double x1=0.0;
				double x2=0.0;
				double y1= 0.0;
				double y2= 0.0;
				for(int s=0;s<4;s++){
					if(fund<=all.get(i).get(s) && fund>=all.get(i).get(s-1)){
						x1=all.get(i).get(s);
						y1=fundy.get(s);
						x2=all.get(i).get(s-1);
						y2=fundy.get(s-1);
					}
				}

				double n = Fuzzification.membership(x1, x2, y1, y2, fund);
				membershipf.add(n);
			}
			else {
				membershipf.add(0.0);
			}
		}




		
		//membership of experience level
		for(int i=0;i<3;i++) {
			if(exp>=allexp.get(i).get(0) && exp<=allexp.get(i).get(2)) {
				double x1=0.0;
				double x2=0.0;
				double y1= 0.0;
				double y2= 0.0;
				for(int s=0;s<3;s++){
					if(exp<=allexp.get(i).get(s) && exp>=allexp.get(i).get(s-1)){
						x1=allexp.get(i).get(s);
						y1=expy.get(s);
						x2=allexp.get(i).get(s-1);
						y2=expy.get(s-1);
					}
				}

				double n = Fuzzification.membership(x1, x2, y1, y2, exp);
				membershipe.add(n);
			}
			else {
				membershipe.add(0.0);
			}
		}
		
		System.out.println("membership of funds "+membershipf);
		System.out.println("membership of experts "+membershipe);
		
		//inference
		String rule1 = "If project_funding is high or team_experience_level is expert then risk is low";
		String[] split1 = rule1.split(" ");
		for(int i=0;i<split1.length;i++){
			if(split1[i].equals("or")){
				//or function (max)
				Double res = (Math.max(membershipf.get(3), membershipe.get(2)));
//				System.out.println("res awel rule : "+res);
				rulesequation.set(0,res);
			}
			else if(split1[i].equals("and")){
//				System.out.println("and function");
				double res= Math.min(membershipf.get(3), membershipe.get(2));
//				System.out.println(res);
			}
		}

		//rule 2  0.5
		String rule2 = "If project_funding is medium and team_experience_level is intermediate or team_experience_level is beginner then risk is normal";
		String[] split2 = rule2.split(" ");
		for(int i=0;i<split2.length;i++){
			if(split2[i].equals("or")){
//				System.out.println("or function");
			}
			else if(split2[i].equals("and")){
				double x= Math.min(membershipf.get(2), membershipe.get(1));
//				System.out.println("res of second rule : "+Math.max(x,membershipf.get(0)));
				rulesequation.set(1,x);
			}
		}

		//rule3 0
		String rule3 = "If project_funding is very low then risk is high.";
		String[] split3 = rule3.split(" ");

		Double res3= membershipf.get(0);
//		System.out.println("res rule 3 : "+res3);
		rulesequation.set(2,res3);

		//rule4 0
		String rule4 = "If project_funding is low and team_experience_level is beginner then risk is high";
		String[] split4 = rule4.split(" ");
		for(int i=0;i<split4.length;i++){
			if(split4[i].equals("or")){
//				System.out.println("or function");
			}
			else if(split4[i].equals("and")){
				double hania= Math.min(membershipf.get(1), membershipe.get(0));
//				System.out.println("res rule 4 : "+hania);
				double w = (Math.max(membershipf.get(0), hania));
				rulesequation.set(2,w);
			}
		}

		System.out.println("rules equations = "+rulesequation);

		Double rlowCent=0.0;
		for (int l=0;l<rlow.size();l++){
			rlowCent+=rlow.get(l);
		}
		rlowCent=rlowCent/rlow.size();
//		System.out.println("rlow centroid = "+rlowCent);


		Double rnormalCent=0.0;
		for (int l=0;l<rnormal.size();l++){
			rnormalCent+=rnormal.get(l);
		}
		rnormalCent=rnormalCent/rnormal.size();
//		System.out.println("rlow centroid = "+rnormalCent);

		Double rhighCent=0.0;
		for (int l=0;l<rhigh.size();l++){
			rhighCent+=rhigh.get(l);
		}
		rhighCent=rhighCent/rhigh.size();
//		System.out.println("rlow centroid = "+rhighCent);

		ArrayList<Double> rcents = new ArrayList<Double>();
		rcents.add(rlowCent);
		rcents.add(rnormalCent);
		rcents.add(rhighCent);
//		System.out.println("rcents = "+rcents);

		Double WieghtedAvg=0.0;
		Double TotalRisk=0.0;
		for(int o=0;o<rulesequation.size();o++){
			TotalRisk+=rulesequation.get(o);
		}
		for(int h=0;h<3;h++){
			WieghtedAvg+=rulesequation.get(h)*rcents.get(h);
		}

//		System.out.println("Weighted = "+WieghtedAvg);
//		System.out.println("risks = "+TotalRisk);
		Double centroid=WieghtedAvg/TotalRisk;
		System.out.println("centroid = "+centroid);

			if(centroid>=rlow.get(0) && centroid<=rlow.get(2)){
				System.out.println("system risk will be low");
			}

			else if(centroid>=rnormal.get(0) && centroid<=rnormal.get(2)){
			System.out.println("system risk will be normal");
			}
			else if(centroid>=rhigh.get(0) && centroid<=rhigh.get(2)){
				System.out.println("system risk will be high");
			}


	}
}
