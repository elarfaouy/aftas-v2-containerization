package com.youcode.aftas.seeder;

import com.youcode.aftas.domain.entity.*;
import com.youcode.aftas.domain.enums.IdentityDocumentType;
import com.youcode.aftas.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class CompetitionSeeder implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final CompetitionRepository competitionRepository;
    private final RankingRepository rankingRepository;
    private final HuntingRepository huntingRepository;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final MemberRepository memberRepository;
    private final LevelRepository levelRepository;
    private final FishRepository fishRepository;

    private Map<String, Permission> permissions = Map.of();
    private Map<String, Role> roles = Map.of();
    private Map<String, User> users = Map.of();
    private Map<String, Fish> fishes = Map.of();

    @Override
    public void run(String... args) throws Exception {
        savePermissions();

        Competition youssoufia = Competition.builder().code("you-18-02-24").location("youssoufia").date(LocalDate.of(2024, 2, 18)).amount(1000.0).startTime(LocalTime.of(8, 0)).endTime(LocalTime.of(12, 0)).build();
        Competition safi = Competition.builder().code("saf-20-02-24").location("safi").date(LocalDate.of(2024, 2, 20)).amount(1000.0).startTime(LocalTime.of(8, 0)).endTime(LocalTime.of(23, 0)).build();
        Competition eljadida = Competition.builder().code("elj-18-02-25").location("eljadida").date(LocalDate.of(2025, 2, 18)).amount(1000.0).startTime(LocalTime.of(8, 0)).endTime(LocalTime.of(12, 0)).build();

        competitionRepository.saveAll(List.of(youssoufia, safi, eljadida));

        Ranking youssoufiaRanking1 = Ranking.builder().id(RankingKey.builder().code(youssoufia.getCode()).num(users.get("adherent").getNum()).build()).score(0).rank(0).build();
        Ranking youssoufiaRanking2 = Ranking.builder().id(RankingKey.builder().code(youssoufia.getCode()).num(users.get("adherent2").getNum()).build()).score(0).rank(0).build();
        Ranking youssoufiaRanking3 = Ranking.builder().id(RankingKey.builder().code(youssoufia.getCode()).num(users.get("adherent3").getNum()).build()).score(0).rank(0).build();

        Ranking safiRanking1 = Ranking.builder().id(RankingKey.builder().code(safi.getCode()).num(users.get("adherent").getNum()).build()).score(0).rank(0).build();

        Ranking eljadidaRanking1 = Ranking.builder().id(RankingKey.builder().code(eljadida.getCode()).num(users.get("adherent4").getNum()).build()).score(0).rank(0).build();

        rankingRepository.saveAll(List.of(youssoufiaRanking1, youssoufiaRanking2, youssoufiaRanking3, safiRanking1, eljadidaRanking1));

        Hunting youssoufiaHunting1 = Hunting.builder().numberOfFish(2).fish(fishes.get("grouper")).member(users.get("adherent")).competition(youssoufia).build();
        Hunting youssoufiaHunting2 = Hunting.builder().numberOfFish(1).fish(fishes.get("tuna")).member(users.get("adherent2")).competition(youssoufia).build();
        Hunting youssoufiaHunting3 = Hunting.builder().numberOfFish(2).fish(fishes.get("shark")).member(users.get("adherent3")).competition(youssoufia).build();
        Hunting youssoufiaHunting4 = Hunting.builder().numberOfFish(1).fish(fishes.get("dorado")).member(users.get("adherent")).competition(youssoufia).build();
        Hunting youssoufiaHunting5 = Hunting.builder().numberOfFish(3).fish(fishes.get("shark")).member(users.get("adherent2")).competition(youssoufia).build();
        Hunting youssoufiaHunting6 = Hunting.builder().numberOfFish(1).fish(fishes.get("grouper")).member(users.get("adherent3")).competition(youssoufia).build();
        Hunting youssoufiaHunting7 = Hunting.builder().numberOfFish(1).fish(fishes.get("wahoo")).member(users.get("adherent")).competition(youssoufia).build();

        Hunting safiHunting1 = Hunting.builder().id(23).numberOfFish(2).fish(fishes.get("amberjack")).member(users.get("adherent")).competition(safi).build();
        Hunting safiHunting2 = Hunting.builder().numberOfFish(3).fish(fishes.get("tuna")).member(users.get("adherent")).competition(safi).build();

        huntingRepository.saveAll(List.of(youssoufiaHunting1, youssoufiaHunting2, youssoufiaHunting3, youssoufiaHunting4, youssoufiaHunting5, youssoufiaHunting6, youssoufiaHunting7, safiHunting1, safiHunting2));
    }

    private void savePermissions() {
        Permission readCompetition = Permission.builder().name("READ_COMPETITION").build();
        Permission writeCompetition = Permission.builder().name("WRITE_COMPETITION").build();
        Permission readMember = Permission.builder().name("READ_MEMBER").build();
        Permission writeMember = Permission.builder().name("WRITE_MEMBER").build();
        Permission readRanking = Permission.builder().name("READ_RANKING").build();
        Permission writeRanking = Permission.builder().name("WRITE_RANKING").build();
        Permission readHunting = Permission.builder().name("READ_HUNTING").build();
        Permission writeHunting = Permission.builder().name("WRITE_HUNTING").build();
        Permission readFish = Permission.builder().name("READ_FISH").build();
        Permission writeLevel = Permission.builder().name("WRITE_LEVEL").build();

        permissionRepository.saveAll(List.of(readCompetition, writeCompetition, readMember, writeMember, readRanking, writeRanking, readHunting, writeHunting, readFish, writeLevel));

        permissions = Map.of(
                "READ_COMPETITION", readCompetition,
                "WRITE_COMPETITION", writeCompetition,
                "READ_MEMBER", readMember,
                "WRITE_MEMBER", writeMember,
                "READ_RANKING", readRanking,
                "WRITE_RANKING", writeRanking,
                "READ_HUNTING", readHunting,
                "WRITE_HUNTING", writeHunting,
                "READ_FISH", readFish,
                "WRITE_LEVEL", writeLevel
        );

        saveRoles();
    }

    private void saveRoles() {
        Role manager = Role.builder().name("MANAGER").permissions(List.of(
                permissions.get("READ_COMPETITION"),
                permissions.get("WRITE_COMPETITION"),
                permissions.get("READ_MEMBER"),
                permissions.get("WRITE_MEMBER"),
                permissions.get("READ_RANKING"),
                permissions.get("WRITE_RANKING"),
                permissions.get("READ_HUNTING"),
                permissions.get("WRITE_HUNTING"),
                permissions.get("READ_FISH"),
                permissions.get("WRITE_LEVEL")
        )).build();

        Role jury = Role.builder().name("JURY").permissions(List.of(
                permissions.get("READ_COMPETITION"),
                permissions.get("WRITE_COMPETITION"),
                permissions.get("READ_MEMBER"),
                permissions.get("READ_RANKING"),
                permissions.get("WRITE_RANKING"),
                permissions.get("READ_HUNTING"),
                permissions.get("WRITE_HUNTING"),
                permissions.get("READ_FISH"),
                permissions.get("WRITE_LEVEL")
        )).build();

        Role adherent = Role.builder().name("ADHERENT").permissions(List.of(
                permissions.get("READ_COMPETITION"),
                permissions.get("READ_MEMBER"),
                permissions.get("READ_RANKING"),
                permissions.get("READ_FISH")
        )).build();

        roleRepository.saveAll(List.of(manager, jury, adherent));

        roles = Map.of(
                "MANAGER", manager,
                "JURY", jury,
                "ADHERENT", adherent
        );

        saveUsers();
    }

    private void saveUsers() {
        User manager = User.builder()
                .num(111)
                .name("John")
                .familyName("Doe")
                .username("manager")
                .password(passwordEncoder.encode("password"))
                .accessionDate(LocalDate.now())
                .nationality("Moroccan")
                .identityNumber("111")
                .identityDocument(IdentityDocumentType.CIN)
                .role(roles.get("MANAGER"))
                .build();

        User jury = User.builder()
                .num(222)
                .name("Jane")
                .familyName("Doe")
                .username("jury")
                .password(passwordEncoder.encode("password"))
                .accessionDate(LocalDate.now())
                .nationality("Moroccan")
                .identityNumber("222")
                .identityDocument(IdentityDocumentType.CIN)
                .role(roles.get("JURY"))
                .build();

        User adherent = User.builder()
                .num(345)
                .name("John")
                .familyName("Doe")
                .username("adherent")
                .password(passwordEncoder.encode("password"))
                .accessionDate(LocalDate.now())
                .nationality("Moroccan")
                .identityNumber("345")
                .identityDocument(IdentityDocumentType.CIN)
                .role(roles.get("ADHERENT"))
                .build();

        User adherent2 = User.builder()
                .num(367)
                .name("John")
                .familyName("Doe")
                .username("adherent2")
                .password(passwordEncoder.encode("password"))
                .accessionDate(LocalDate.now())
                .nationality("Moroccan")
                .identityNumber("367")
                .identityDocument(IdentityDocumentType.CIN)
                .role(roles.get("ADHERENT"))
                .build();

        User adherent3 = User.builder()
                .num(389)
                .name("John")
                .familyName("Doe")
                .username("adherent3")
                .password(passwordEncoder.encode("password"))
                .accessionDate(LocalDate.now())
                .nationality("Moroccan")
                .identityNumber("389")
                .identityDocument(IdentityDocumentType.CIN)
                .role(roles.get("ADHERENT"))
                .build();

        User adherent4 = User.builder()
                .num(300)
                .name("John")
                .familyName("Doe")
                .username("adherent4")
                .password(passwordEncoder.encode("password"))
                .accessionDate(LocalDate.now())
                .nationality("Moroccan")
                .identityNumber("300")
                .identityDocument(IdentityDocumentType.CIN)
                .role(roles.get("ADHERENT"))
                .build();

        memberRepository.saveAll(List.of(manager, jury, adherent, adherent2, adherent3, adherent4));

        users = Map.of(
                "manager", manager,
                "jury", jury,
                "adherent", adherent,
                "adherent2", adherent2,
                "adherent3", adherent3,
                "adherent4", adherent4
        );

        saveFishes();
    }

    private void saveFishes() {
        Level firstLevel = Level.builder().code(1).points(1000).description("first level").build();
        Level secondLevel = Level.builder().code(2).points(2000).description("second level").build();
        Level thirdLevel = Level.builder().code(3).points(3000).description("third level").build();
        Level fourthLevel = Level.builder().code(4).points(4000).description("fourth level").build();

        levelRepository.saveAll(List.of(firstLevel, secondLevel, thirdLevel, fourthLevel));

        Fish grouper = Fish.builder().name("Grouper").averageWeight(15.0).level(firstLevel).build();
        Fish tuna = Fish.builder().name("Tuna").averageWeight(20.0).level(secondLevel).build();
        Fish shark = Fish.builder().name("Shark").averageWeight(25.0).level(thirdLevel).build();
        Fish swordfish = Fish.builder().name("Swordfish").averageWeight(30.0).level(fourthLevel).build();
        Fish marlin = Fish.builder().name("Marlin").averageWeight(35.0).level(firstLevel).build();
        Fish sailfish = Fish.builder().name("Sailfish").averageWeight(40.0).level(secondLevel).build();
        Fish dorado = Fish.builder().name("Dorado").averageWeight(45.0).level(thirdLevel).build();
        Fish wahoo = Fish.builder().name("Wahoo").averageWeight(50.0).level(fourthLevel).build();
        Fish amberjack = Fish.builder().name("Amberjack").averageWeight(55.0).level(firstLevel).build();
        Fish kingfish = Fish.builder().name("Kingfish").averageWeight(60.0).level(secondLevel).build();
        Fish barracuda = Fish.builder().name("Barracuda").averageWeight(65.0).level(thirdLevel).build();
        Fish cobia = Fish.builder().name("Cobia").averageWeight(70.0).level(fourthLevel).build();

        fishRepository.saveAll(List.of(grouper, tuna, shark, swordfish, marlin, sailfish, dorado, wahoo, amberjack, kingfish, barracuda, cobia));

        fishes = Map.of(
                "grouper", grouper,
                "tuna", tuna,
                "shark", shark,
                "swordfish", swordfish,
                "marlin", marlin,
                "sailfish", sailfish,
                "dorado", dorado,
                "wahoo", wahoo,
                "amberjack", amberjack,
                "kingfish", kingfish
        );
    }
}
