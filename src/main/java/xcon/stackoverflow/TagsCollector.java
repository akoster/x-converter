package xcon.stackoverflow;

import java.util.*;

public class TagsCollector {

    class Story {

        private final long id;
        private final String title;
        private final String keyword;
        private final String targeting;

        public Story(long id, String title, String keyword, String targeting) {
            this.id = id;
            this.title = title;
            this.keyword = keyword;
            this.targeting = targeting;
        }

        public long getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getKeyword() {
            return keyword;
        }

        public String getTargeting() {
            return targeting;
        }

        @Override
        public String toString() {
            return String.format("Story %s, title=%s, keyword=%s, targeting=%s", id, title, keyword, targeting);
        }
    }

    class Tags {
        private final long id;
        private final String title;
        private final List<String> keywords = new ArrayList<>();
        private final List<String> targetings = new ArrayList<>();

        Tags(Story story) {
            this.id = story.id;
            this.title = story.title;
        }

        public List<String> getKeywords() {
            return keywords;
        }

        public List<String> getTargetings() {
            return targetings;
        }

        @Override
        public String toString() {
            return String.format("Tags for id %s, title:%s: keywords=%s, targetings=%s", id, title, keywords, targetings);
        }
    }

    public static void main(String[] args) {
        TagsCollector app = new TagsCollector();
        app.go();
    }

    private void go() {
        List<Story> stories = createStories();
        System.out.println(stories);
        Map<Long, Tags> tagsById = collectTags(stories);
        tagsById.forEach((aLong, tags) -> System.out.println(tags));
    }

    private List<Story> createStories() {
        return Arrays.asList(
                new Story(1, "Onboarding", "new joinee", "finance"),
                new Story(1, "Onboarding", "training", "HR")
        );
    }

    private Map<Long, Tags> collectTags(List<Story> stories) {
        Map<Long, Tags> tagsById = new HashMap<>();
        stories.forEach(s -> {
            Tags tags = tagsById.computeIfAbsent(s.id, v -> new Tags(s));
            tags.getKeywords().add(s.getKeyword());
            tags.getTargetings().add(s.getTargeting());
        });
        return tagsById;
    }

}
