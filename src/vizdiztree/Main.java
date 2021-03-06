package vizdiztree;
import org.bson.Document;
import vizdiztree.answer.*;
import vizdiztree.survey.*;
import vizdiztree.surveytree.SurveyTree;

//import supercsv.*;
import java.util.ArrayList;

public class Main {
    public static String getHello() { return "Hello JSP"; }

    public static Survey testSurvey() {
        Question q = testQuestion();

        //Testing Survey Class methods
        Survey s= new Survey("Should I Buy this Car?");
        System.out.println(s.getTitle());
        s.setTitle("Should I buy this House?");
        System.out.println(s.getTitle());
        s.addQuestion(q);

        ArrayList<String> ans2= new ArrayList<>();
        ans2.add("Yes");
        ans2.add("No");
        s.addQuestion("Is it new?", ans2);

        for(Question q1: s.getQuestions()){
            System.out.println("Question: "+ q1.getTitle());
            System.out.println("Possible Answers ");
            for(Answer a: q1.getAnswers()){
                System.out.println( a.getTitle());
            }
        }
        return s;
    }

    public static Question testQuestion() {
        //Testing Question class methods
        ArrayList<String> ans1= new ArrayList<>();
        ans1.add("<2000");
        ans1.add("2000-4000");
        ans1.add(">4000");
        Question q= new Question("Car Price?",ans1);
        System.out.println(q.getTitle());
        for(Answer a: q.getAnswers()){
            System.out.println("Possible Answers " + a.getTitle());
        }
        q.setTitle("House Price?");
        System.out.println(q.getTitle());
        return q;
    }

    public static Answer testAnswer() {
        //Test Answer class methods
        Answer ans= new Answer("Yes");
        System.out.println(ans.getTitle());
        ans.setTitle("No");
        System.out.println(ans.getTitle());
        return ans;
    }

    public static void testTakeSurvey() {
        Survey s = testSurvey();
        //Test take survey
        for(Question q2: s.getQuestions()){
            System.out.print(q2.getTitle()+" ");
            System.out.println("");
        }
        ArrayList<SurveyResponse> r=s.takeSurvey();
        boolean flag=true;
        for(SurveyResponse response: r){
            if(flag){
                response.addResponse("Yes");
            }
            else{
                response.addResponse("No");
            }
            flag=!flag;
            System.out.println(response.getResponses());
        }

        s.createKeys();
        System.out.println(s.getKeys());

        System.out.println(s.convertResponses());


        SurveyTree st =  new SurveyTree();
        st.setSurvey(s);
        try { st.buildTree(); } catch (Exception e) { e.printStackTrace();}

        st.writeData(s.getConvertedResponses());
        st.writeQuestions();
    }

    public static void testClasses() {
        testAnswer();
        testQuestion();
        testSurvey();
        testTakeSurvey();
    }

    public static void testMongo() {
        if (SurveyAdmin.tryLogin("admin", "password")) {
            try {
                SurveyAdmin admin = new SurveyAdmin("admin", "password");
                Survey s = testSurvey();
                admin.addSurvey(s);
                System.out.println("Valid Login");
            } catch(Exception e) {}
        } else {
            System.out.println("Invalid login");
        }
    }

    public static void main(String[] args) {
      //testClasses();
        testMongo();


    }
}