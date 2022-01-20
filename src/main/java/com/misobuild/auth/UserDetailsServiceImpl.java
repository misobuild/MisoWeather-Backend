package com.misobuild.auth;

import com.misobuild.domain.member.Member;
import com.misobuild.domain.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private MemberRepository memberRepository;

    public UserDetails loadUserByUsername(String userPk){
        Member member = memberRepository.findById(Long.parseLong(userPk))
                .orElseThrow(() -> new UsernameNotFoundException("NOTFOUND"));
        return new com.misobuild.auth.UserDetailsImpl(member);
    }
}
