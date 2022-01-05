package com.misobuild.auth;

import com.misobuild.domain.Member;
import com.misobuild.domain.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private MemberRepository memberRepository;

    //이름은 loadByUsername이지만
    // OAuth2 방식으로 구현할때 유니크 값은 userId이다.
    public UserDetails loadUserByUsername(String userPk) throws UsernameNotFoundException {
        Member member = memberRepository.findById(Long.parseLong(userPk))
                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + userPk));

        return new com.misobuild.auth.UserDetailsImpl(member);
    }
}
