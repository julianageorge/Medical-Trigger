package CaseStudy;

import Fuzzy.Rules.Rule;
import Fuzzy.Rules.RuleBase;
import Fuzzy.inference.InferenceEngine;
import Fuzzy.inference.MamdaniEngine;
import Fuzzy.memberShip.TrapezoidMF;
import Fuzzy.memberShip.TriangleMF;
import Fuzzy.operator.MaxSNorm;
import Fuzzy.operator.MinTnorm;
import Fuzzy.variables.FuzzySet;
import Fuzzy.variables.LinguisticVariable;

import java.util.HashMap;
import java.util.Map;

/**
 * Medical Trigger System - Fuzzy Logic Case Study
 * 
 * This system automatically decides whether to trigger a medical alert
 * based on a patient's vital signs using fuzzy logic.
 * 
 * Input Variables:
 * - Heart Rate (HR): Low / Normal / High (range: 40-120 bpm)
 * - Body Temperature: Low / Normal / High (range: 95-105°F)
 * - Blood Pressure (BP): Low / Normal / High (range: 60-180 mmHg)
 * - Oxygen Level (SpO₂): Low / Normal / High (range: 70-100%)
 * 
 * Output Variable:
 * - Medical Alert Level: None / Moderate / High (range: 0-100)
 */
public class MedicalTrigger {
    
    private Map<String, LinguisticVariable> variables;
    private RuleBase ruleBase;
    private InferenceEngine inferenceEngine;
    
    public MedicalTrigger() {
        setupVariables();
        setupRules();
        setupInferenceEngine();
    }
    
    /**
     * Setup input and output linguistic variables with membership functions
     */
    private void setupVariables() {
        variables = new HashMap<>();
        
        // ========== Heart Rate (HR) ==========
        // Range: 40-120 bpm
        // Low: <60 bpm (bradycardia)
        // Normal: 60-100 bpm
        // High: >100 bpm (tachycardia)
        LinguisticVariable heartRate = new LinguisticVariable("HeartRate", 40, 120);
        heartRate.addFuzzySet(new FuzzySet("Low", new TrapezoidMF(40, 40, 50, 60)));
        heartRate.addFuzzySet(new FuzzySet("Normal", new TriangleMF(60, 80, 100)));
        heartRate.addFuzzySet(new FuzzySet("High", new TrapezoidMF(100, 110, 120, 120)));
        variables.put("HeartRate", heartRate);
        
        // ========== Body Temperature ==========
        // Range: 95-105°F
        // Low: <97°F (hypothermia)
        // Normal: 97-99.5°F
        // High: >99.5°F (fever)
        LinguisticVariable temperature = new LinguisticVariable("Temperature", 95, 105);
        temperature.addFuzzySet(new FuzzySet("Low", new TrapezoidMF(95, 95, 96, 97)));
        temperature.addFuzzySet(new FuzzySet("Normal", new TriangleMF(97, 98.25, 99.5)));
        temperature.addFuzzySet(new FuzzySet("High", new TrapezoidMF(99.5, 100.5, 105, 105)));
        variables.put("Temperature", temperature);
        
        // ========== Blood Pressure (BP) ==========
        // Range: 60-180 mmHg (systolic)
        // Low: <90 mmHg (hypotension)
        // Normal: 90-140 mmHg
        // High: >140 mmHg (hypertension)
        LinguisticVariable bloodPressure = new LinguisticVariable("BloodPressure", 60, 180);
        bloodPressure.addFuzzySet(new FuzzySet("Low", new TrapezoidMF(60, 60, 80, 90)));
        bloodPressure.addFuzzySet(new FuzzySet("Normal", new TriangleMF(90, 115, 140)));
        bloodPressure.addFuzzySet(new FuzzySet("High", new TrapezoidMF(140, 150, 180, 180)));
        variables.put("BloodPressure", bloodPressure);
        
        // ========== Oxygen Level (SpO₂) ==========
        // Range: 70-100%
        // Low: <90% (hypoxemia)
        // Normal: 90-100%
        // High: Not clinically relevant, but included for completeness
        LinguisticVariable oxygenLevel = new LinguisticVariable("OxygenLevel", 70, 100);
        oxygenLevel.addFuzzySet(new FuzzySet("Low", new TrapezoidMF(70, 70, 85, 90)));
        oxygenLevel.addFuzzySet(new FuzzySet("Normal", new TriangleMF(90, 95, 100)));
        oxygenLevel.addFuzzySet(new FuzzySet("High", new TrapezoidMF(98, 99, 100, 100)));
        variables.put("OxygenLevel", oxygenLevel);
        
        // ========== Medical Alert Level (Output) ==========
        // Range: 0-100
        // None: 0-30 (no alert needed)
        // Moderate: 25-70 (monitor closely)
        // High: 60-100 (immediate attention required)
        LinguisticVariable alertLevel = new LinguisticVariable("AlertLevel", 0, 100);
        alertLevel.addFuzzySet(new FuzzySet("None", new TrapezoidMF(0, 0, 20, 30)));
        alertLevel.addFuzzySet(new FuzzySet("Moderate", new TriangleMF(25, 47.5, 70)));
        alertLevel.addFuzzySet(new FuzzySet("High", new TrapezoidMF(60, 80, 100, 100)));
        variables.put("AlertLevel", alertLevel);
    }
    
    /**
     * Setup fuzzy rules for medical alert decision making
     * Rules are based on medical knowledge and clinical guidelines
     */
    private void setupRules() {
        ruleBase = new RuleBase();
        
        // Rule 1: Critical - High HR AND High Temp → High Alert
        Map<String, String> ant1 = new HashMap<>();
        ant1.put("HeartRate", "High");
        ant1.put("Temperature", "High");
        Map<String, String> cons1 = new HashMap<>();
        cons1.put("AlertLevel", "High");
        ruleBase.createRule(new Rule(1, ant1, cons1));
        
        // Rule 2: Critical - Low HR AND Low SpO₂ → High Alert
        Map<String, String> ant2 = new HashMap<>();
        ant2.put("HeartRate", "Low");
        ant2.put("OxygenLevel", "Low");
        Map<String, String> cons2 = new HashMap<>();
        cons2.put("AlertLevel", "High");
        ruleBase.createRule(new Rule(2, ant2, cons2));
        
        // Rule 3: Critical - High BP AND High Temp → High Alert
        Map<String, String> ant3 = new HashMap<>();
        ant3.put("BloodPressure", "High");
        ant3.put("Temperature", "High");
        Map<String, String> cons3 = new HashMap<>();
        cons3.put("AlertLevel", "High");
        ruleBase.createRule(new Rule(3, ant3, cons3));
        
        // Rule 4: Critical - Low BP AND Low SpO₂ → High Alert
        Map<String, String> ant4 = new HashMap<>();
        ant4.put("BloodPressure", "Low");
        ant4.put("OxygenLevel", "Low");
        Map<String, String> cons4 = new HashMap<>();
        cons4.put("AlertLevel", "High");
        ruleBase.createRule(new Rule(4, ant4, cons4));
        
        // Rule 5: Moderate - High HR OR High Temp → Moderate Alert
        Map<String, String> ant5 = new HashMap<>();
        ant5.put("HeartRate", "High");
        Map<String, String> cons5 = new HashMap<>();
        cons5.put("AlertLevel", "Moderate");
        ruleBase.createRule(new Rule(5, ant5, cons5));
        
        // Rule 6: Moderate - Low HR OR Low SpO₂ → Moderate Alert
        Map<String, String> ant6 = new HashMap<>();
        ant6.put("OxygenLevel", "Low");
        Map<String, String> cons6 = new HashMap<>();
        cons6.put("AlertLevel", "Moderate");
        ruleBase.createRule(new Rule(6, ant6, cons6));
        
        // Rule 7: Moderate - High BP OR Low BP → Moderate Alert
        Map<String, String> ant7 = new HashMap<>();
        ant7.put("BloodPressure", "High");
        Map<String, String> cons7 = new HashMap<>();
        cons7.put("AlertLevel", "Moderate");
        ruleBase.createRule(new Rule(7, ant7, cons7));
        
        Map<String, String> ant8 = new HashMap<>();
        ant8.put("BloodPressure", "Low");
        Map<String, String> cons8 = new HashMap<>();
        cons8.put("AlertLevel", "Moderate");
        ruleBase.createRule(new Rule(8, ant8, cons8));
        
        // Rule 9: Normal - All vitals normal → No Alert
        Map<String, String> ant9 = new HashMap<>();
        ant9.put("HeartRate", "Normal");
        ant9.put("Temperature", "Normal");
        ant9.put("BloodPressure", "Normal");
        ant9.put("OxygenLevel", "Normal");
        Map<String, String> cons9 = new HashMap<>();
        cons9.put("AlertLevel", "None");
        ruleBase.createRule(new Rule(9, ant9, cons9));
        
        // Rule 10: Normal - BP and Temp normal → No Alert
        Map<String, String> ant10 = new HashMap<>();
        ant10.put("BloodPressure", "Normal");
        ant10.put("Temperature", "Normal");
        Map<String, String> cons10 = new HashMap<>();
        cons10.put("AlertLevel", "None");
        ruleBase.createRule(new Rule(10, ant10, cons10));
        
        // Rule 11: Moderate - Low Temp → Moderate Alert
        Map<String, String> ant11 = new HashMap<>();
        ant11.put("Temperature", "Low");
        Map<String, String> cons11 = new HashMap<>();
        cons11.put("AlertLevel", "Moderate");
        ruleBase.createRule(new Rule(11, ant11, cons11));
        
        // Rule 12: Critical - High HR AND Low SpO₂ → High Alert
        Map<String, String> ant12 = new HashMap<>();
        ant12.put("HeartRate", "High");
        ant12.put("OxygenLevel", "Low");
        Map<String, String> cons12 = new HashMap<>();
        cons12.put("AlertLevel", "High");
        ruleBase.createRule(new Rule(12, ant12, cons12));
    }
    
    /**
     * Setup inference engine (Mamdani with Min T-norm and Max S-norm)
     */
    private void setupInferenceEngine() {
        MinTnorm minTnorm = new MinTnorm();
        MaxSNorm maxSNorm = new MaxSNorm();
        inferenceEngine = new MamdaniEngine(variables, minTnorm, maxSNorm);
    }
    
    /**
     * Evaluate medical alert level based on patient vital signs
     * 
     * @param heartRate Heart rate in bpm (40-120)
     * @param temperature Body temperature in °F (95-105)
     * @param bloodPressure Blood pressure in mmHg (60-180)
     * @param oxygenLevel Oxygen saturation in % (70-100)
     * @return Alert level (0-100, where 0-33≈None, 33-66≈Moderate, 66-100≈High)
     */
    public double evaluate(double heartRate, double temperature, 
                          double bloodPressure, double oxygenLevel) {
        Map<String, Double> inputs = new HashMap<>();
        inputs.put("HeartRate", heartRate);
        inputs.put("Temperature", temperature);
        inputs.put("BloodPressure", bloodPressure);
        inputs.put("OxygenLevel", oxygenLevel);
        
        return inferenceEngine.evaluate(inputs, ruleBase);
    }
    
    /**
     * Get a human-readable alert level description
     */
    public String getAlertDescription(double alertValue) {
        if (alertValue < 33) {
            return "None - No immediate action needed";
        } else if (alertValue < 66) {
            return "Moderate - Monitor closely";
        } else {
            return "High - Immediate attention required";
        }
    }
    
    /**
     * Display fuzzification results for debugging
     */
    public void displayFuzzification(double heartRate, double temperature, 
                                    double bloodPressure, double oxygenLevel) {
        System.out.println("\n=== Fuzzification Results ===");
        variables.get("HeartRate").fuzzify(heartRate);
        variables.get("Temperature").fuzzify(temperature);
        variables.get("BloodPressure").fuzzify(bloodPressure);
        variables.get("OxygenLevel").fuzzify(oxygenLevel);
    }
    
    public static void main(String[] args) {
        MedicalTrigger system = new MedicalTrigger();
        
        System.out.println("==========================================");
        System.out.println("Medical Trigger System - Fuzzy Logic");
        System.out.println("==========================================\n");
        
        // Test Case 1: Critical patient - High HR and High Temp
        System.out.println("Test Case 1: Critical Patient");
        System.out.println("HR: 110 bpm, Temp: 102°F, BP: 130 mmHg, SpO₂: 95%");
        double alert1 = system.evaluate(110, 102, 130, 95);
        System.out.printf("Alert Level: %.2f - %s\n", alert1, system.getAlertDescription(alert1));
        system.displayFuzzification(110, 102, 130, 95);
        
        // Test Case 2: Normal patient
        System.out.println("\n\nTest Case 2: Normal Patient");
        System.out.println("HR: 75 bpm, Temp: 98.5°F, BP: 120 mmHg, SpO₂: 98%");
        double alert2 = system.evaluate(75, 98.5, 120, 98);
        System.out.printf("Alert Level: %.2f - %s\n", alert2, system.getAlertDescription(alert2));
        system.displayFuzzification(75, 98.5, 120, 98);
        
        // Test Case 3: Moderate concern - Low SpO₂
        System.out.println("\n\nTest Case 3: Moderate Concern");
        System.out.println("HR: 85 bpm, Temp: 98°F, BP: 110 mmHg, SpO₂: 88%");
        double alert3 = system.evaluate(85, 98, 110, 88);
        System.out.printf("Alert Level: %.2f - %s\n", alert3, system.getAlertDescription(alert3));
        system.displayFuzzification(85, 98, 110, 88);
        
        // Test Case 4: Critical - Low HR and Low SpO₂
        System.out.println("\n\nTest Case 4: Critical - Bradycardia & Hypoxemia");
        System.out.println("HR: 50 bpm, Temp: 97.5°F, BP: 100 mmHg, SpO₂: 85%");
        double alert4 = system.evaluate(50, 97.5, 100, 85);
        System.out.printf("Alert Level: %.2f - %s\n", alert4, system.getAlertDescription(alert4));
        system.displayFuzzification(50, 97.5, 100, 85);
        
        // Test Case 5: High BP
        System.out.println("\n\nTest Case 5: Hypertension");
        System.out.println("HR: 80 bpm, Temp: 98°F, BP: 160 mmHg, SpO₂: 96%");
        double alert5 = system.evaluate(80, 98, 160, 96);
        System.out.printf("Alert Level: %.2f - %s\n", alert5, system.getAlertDescription(alert5));
        system.displayFuzzification(80, 98, 160, 96);
        
        System.out.println("\n==========================================");
        System.out.println("Design Justification:");
        System.out.println("==========================================");
        System.out.println("1. Input Variables:");
        System.out.println("   - Heart Rate: Based on clinical ranges (bradycardia <60, normal 60-100, tachycardia >100)");
        System.out.println("   - Temperature: Based on normal body temp (hypothermia <97°F, normal 97-99.5°F, fever >99.5°F)");
        System.out.println("   - Blood Pressure: Based on AHA guidelines (hypotension <90, normal 90-140, hypertension >140)");
        System.out.println("   - Oxygen Level: Based on SpO₂ standards (hypoxemia <90%, normal 90-100%)");
        System.out.println("\n2. Membership Functions:");
        System.out.println("   - Trapezoid for edge cases (Low/High) to allow gradual transitions");
        System.out.println("   - Triangle for Normal ranges to provide clear peak membership");
        System.out.println("   - Overlapping ranges ensure smooth transitions");
        System.out.println("\n3. Rules:");
        System.out.println("   - Critical combinations (High+High, Low+Low) → High Alert");
        System.out.println("   - Single abnormal vital → Moderate Alert");
        System.out.println("   - All normal → No Alert");
        System.out.println("   - Rules prioritize patient safety (err on side of caution)");
        System.out.println("\n4. Output:");
        System.out.println("   - 0-100 scale for flexibility");
        System.out.println("   - Three levels: None (0-33), Moderate (33-66), High (66-100)");
        System.out.println("   - Overlapping ranges allow smooth transitions between alert levels");
    }
}
