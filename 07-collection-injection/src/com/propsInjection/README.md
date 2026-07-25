# Properties Injection - XML Approach

**Scenario:** A small `MailSender` component (classic real-world `Properties`
use — JavaMail/SMTP config) plus an external, environment-specific config file
loaded via `<util:properties />`.

**Corner cases / things worth knowing, baked into the example:**

- **`java.util.Properties` extends `Hashtable<Object, Object>`** — but by
  convention (and by what Spring's `<props>` element even allows you to write),
  you only ever put `String` keys/values in it. Unlike `<map>`, `<props>`
  **has no `key-ref`/`value-ref`** — you cannot inject a bean reference as a
  Properties value. That's a deliberate limitation, since Properties exists for
  simple config, not object graphs.
- **No ordering guarantee at all.** `<list>`/`<set>`/`<map>` default to
  order-preserving implementations (`ArrayList`/`LinkedHashSet`/`LinkedHashMap`).
  `Properties` is `Hashtable`-based — iteration order is unspecified and you
  should never rely on it.
- **Duplicate keys → last one silently wins** — same rule as `Map`, shown again
  here for reinforcement.
- **Two ways to populate a Properties value in XML:**
  - the structured `<props><prop key="...">val</prop></props>` form, and,
  - a shortcut — a plain multi-line `<value>` block, which Spring parses using
     the same rules as `Properties.load()` (i.e., pasted straight from a
     `.properties` file).
- **`<util:properties>` with a `location` attribute** loads an entire external
  `.properties` file from the classpath — the realistic way config is usually
  done, rather than hardcoding every key in XML.
- **getProperty(key, default)** — a convenience Properties gives you over plain
  `Map.get()`, useful for optional/missing config.


## Program Sample Run Output

```txt
######### Properties Injection (XML) Demo ################


appDefaults.getClass() => class java.util.Properties
appDefaults: {app.version=1.4.2, app.timezone=Asia/Kolkata, app.environment=production}   (Hashtable-based -> iteration order NOT guaranteed, unlike the default List/Set/Map implementations)
appDefaults.getProperty("app.version") => 1.4.2
appDefaults.getProperty("app.region", "ap-south-1") => ap-south-1   (key doesn't exist -> falls back to the supplied default)

MailSender [mailServerSettings={mail.smtp.starttls.enable=true, mail.smtp.port=2525, mail.smtp.host=smtp.mailtrap.io, mail.smtp.auth=true}, featureFlags={betaSearch=false, darkMode=true, maxUploadSizeMB=25}]

mailSettings.getProperty("mail.smtp.port") => 2525   (2 <prop key="mail.smtp.port"> entries declared in XML -> last one, 2525, wins)

featureFlags (parsed from a plain <value> block, Properties.load()-style): {betaSearch=false, darkMode=true, maxUploadSizeMB=25}
featureFlags.getProperty("darkMode") => true


```
