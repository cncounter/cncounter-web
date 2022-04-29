package com.cncounter.test;


import io.krakens.grok.api.*;

import java.util.regex.Pattern;


public class TestGrokCompiler {
    public static void main(String[] args) {
        GrokCompiler grokCompiler = GrokCompiler.newInstance();
        grokCompiler.registerDefaultPatterns();
        grokCompiler.register("DNS_QUERY_TYPE", "(?:Q|U|R Q|R U)");
        grokCompiler.register("DNS_FLAGS_CHARS", "(A| |T|D|DR|A DR|T DR)");


        String patternRegex = "%{GREEDYDATA:log_time} %{SPACE}%{WORD:dns_thread_id}%{SPACE}%{WORD:dns_context}%{SPACE}%{WORD:dns_internal_packet_identifier}%{SPACE}%{WORD:dns_protocol}%{SPACE}%{WORD:dns_direction}%{SPACE}%{IP:dns_ip}%{SPACE}%{WORD:dns_xid}%{SPACE}%{DNS_QUERY_TYPE:dns_query_type}%{SPACE}\\[%{GREEDYDATA:dns_flags_hex}%{SPACE}%{DNS_FLAGS_CHARS:dns_flags_chars}%{SPACE}%{WORD:dns_response_code}\\]%{SPACE}%{WORD:dns_question_type}%{SPACE}%{GREEDYDATA:dns_question_name}";
        //patternRegex = "((?:Q|U|R Q|R U))((A| |T|D|DR|A DR|T DR))";
        //Pattern.compile(patternRegex);
        Grok compile = grokCompiler.compile(patternRegex);
        Match match = compile.match("2022/3/8 10:38:29 16F8 PACKET  000001B8EEECF890 UDP Snd 10.234.209.187  da3b R Q [8081   DR  NOERROR] 65     (9)substrate(6)office(3)com(0)");
        System.out.println(match.capture());
    }
}
