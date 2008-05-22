package jrelcal.ci;

import jrelcal.Pair;
import jrelcal.sets.*;

public class ContinuousIntegration {
    
    
    class Build implements Comparable<Build> {
        private String _name;
        private Integer _id;
        
        public Build(String name, int id) {
            _name = name;
            _id = id;
        }
        
        
        public String getName() {
            return _name;
        }
        
        public long getSecondSinceEpoch() {
            return 0;
        }
        
        public Integer getId() {
            return _id;
        }
        
        public int compareTo(Build b) {
            int n = getName().compareTo(b.getName());
            if (n != 0) {
                return n;
            }
            return getId().compareTo(b.getId());
        }
        
        public String toString() {
            return "<" + getName() + ", " + getId() + ">";
        }
        
    }
        
    public static void main(String args[]) {
        ContinuousIntegration ci = new ContinuousIntegration();
        ci.example();
    }

    
    public void pseudo(String root, Set<String> deps, Set<Build> success, AdjacencyTableRelation<Build,Build> integration) {
        
        int slot = 7 * 60 * 60 * 24 * 1000;
        long now = System.currentTimeMillis();
        
        Set<Set<Build>> extents = new Set<Set<Build>>();
        do {
            /* && b.getSecondSinceEpoch() > now - slot */
            Relation<String, Build> classification = new AdjacencyTableRelation<String,Build>();
            for (Build b: success) {    
                if (deps.contains(b.getName()) ) {
                    classification.add(new Pair<String,Build>(b.getName(), b));
                }           
            }
            Set<Set<Build>> partitioning = new Set<Set<Build>>();
            for (String d: deps) {
                partitioning.add(classification.image(d));
            }
            Set<Set<Build>> contexts = Set.biggerProduct(partitioning); 
            Relation<Build, Build> closure = AdjacencyTableRelation.reflexiveTransitiveClosure(integration);
            for (Set<Build> context: contexts) {
                Set<Build> extent = closure.image(context);
                boolean homogenous = true;
                
                Relation<String, Build> subclass= new AdjacencyTableRelation<String,Build>();
                for (Build b: extent) {
                    subclass.add(new Pair<String,Build>(b.getName(), b));
                }
                for (String name: subclass.domain()) {
                    if (subclass.image(name).size() > 1) {
                        homogenous = false;
                        break;
                    }
                }
                if (homogenous) {
                    extents.add(extent);
                }
            }
            
        
        } 
        while (extents.size() == 0);
        // return newest extent
    }

        
    

    public void example() {
        Set<Build> builds = new Set<Build>();
        builds.add(new Build("A", 1));
        builds.add(new Build("B", 1));
        builds.add(new Build("B", 2));
        builds.add(new Build("C", 1));
        builds.add(new Build("C", 2));
        builds.add(new Build("D", 1));
        
        Relation<String, Build> classification = new AdjacencyTableRelation<String,Build>();
        for (Build b: builds) {
            classification.add(new Pair<String,Build>(b.getName(), b));
        }
        Set<String> D = classification.domain().difference(new Set<String>("A"));
        Set<Set<Build>> buildContexts = new Set<Set<Build>>();
        for (String d: D) {
            buildContexts.add(classification.image(d));
        }
        System.out.println(Set.biggerProduct(buildContexts));   
    }
    
    
    
}
