package com.example.mockinterview.service;

import com.example.mockinterview.entity.*;
import com.example.mockinterview.repository.SessionParticipantsRepository;
import com.example.mockinterview.repository.SessionRepository;
import com.example.mockinterview.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SessionParticipantsRepository participantsRepository;

    @Autowired
    private UserRepository userRepository;

    // Create session & invite random student
    public Session createSession(Long userId, String topic) {
        User creator = userRepository.findById(userId).orElseThrow();
        List<User> available = userRepository.findByPreferenceAndStatusOrderByIdAsc(creator.getPreference(), Status.ACTIVE);

        User matched = null;
        for(User u : available) {
            if(!u.getId().equals(userId)) { // skip self
                matched = u;
                break;
            }
        }

        Session session = new Session();
        session.setTopic(topic);
        session.setStatus(SessionStatus.PENDING);
        session.setRoomLink("https://meet.google.com/" + generateRandomRoom());
        session.setCreatedAt(LocalDateTime.now());
        session.setUpdatedAt(LocalDateTime.now());
        sessionRepository.save(session);

        // Add participants
        addParticipant(session, creator);
        if(matched != null) addParticipant(session, matched);

        return session;
    }

    private void addParticipant(Session session, User user) {
        SessionParticipants sp = new SessionParticipants();
        sp.setSessionId(session.getId());
        sp.setUserId(user.getId());
        sp.setStatus(ParticipantStatus.INVITED);
        participantsRepository.save(sp);
    }

    private String generateRandomRoom() {
        String chars = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<10;i++) sb.append(chars.charAt(random.nextInt(chars.length())));
        return sb.toString();
    }
}