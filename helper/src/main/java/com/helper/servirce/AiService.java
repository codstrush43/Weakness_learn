package com.helper.servirce;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.helper.entity.Tag;

@Service
public class AiService {

    private final ChatClient chatClient;

    public AiService(ChatClient.Builder builder)
    {
        this.chatClient=builder.build();
    }
    
    private String _prompt="You are a codeforces teacher.\n"+
                            "you need to teach student via mcq.\n"+
                            "you need to generate mcqs based on lvl of weekness.\n"+
                            "i have devided topic based on lvl 1 is low,2 is mid,3 is high.\n"+
                            "in low lvl, student does not understand much about that topic thus generate basic mcqs about that topic(eg: why do we need to use that topic(techniue)?).\n"+
                            "in mid lvl, student had basic understanding of that topic nut it does not figure out that when and where to use that topic(technique) like they remember the pattern thus generate the schenario based question(eg: if i have 1e5 data and i have solution in recurssion based which take exponantial time then whish topic is used for time optimization?).\n"+
                            "in high lvl, student have solved enaugh questions for understand that questions thus identify the dificult topics from that and ask some intermidiate mcq for that.\n"+
                            "you need to return the JSON object which hold question,options,ans,explaination thats it do not write extra stiring or extra ccontent in it\n"+
                            "i can give you topics into 1:topics;2:topics;3:topics; formate.\n"+
                            "generate only total 10 questions based one difficulty like first generate the low difficulty questions anad then for mid and then for high if there are too many tags which are not fir for 10 questions then select tags randomly and generate mcq."+
                            "the topics are:-\n";
    public String generateQuestion(List<Tag> low,List<Tag> mid,List<Tag> high)
    {
            _prompt+="1 : ";
            for(Tag tag : low)
            {
                _prompt+=tag.getName();
                _prompt+=", ";
            }
            _prompt+=";";

            _prompt+="2 : ";
            for(Tag tag : mid)
            {
                _prompt+=tag.getName();
                _prompt+=", ";
            }
            _prompt+=";";

            _prompt+="3 : ";
            for(Tag tag : high)
            {
                _prompt+=tag.getName();
                _prompt+=", ";
            }
            _prompt+=";";
            
        return chatClient.prompt(_prompt)
                        .call()
                        .content();
    }

}
