## Author Thomas Bracher "thomas.bracher@cpe.fr"
require 'selenium-webdriver'
require 'uri'

class SmokeTest < Test::Unit::TestCase
    def setup
        selenium_uri = URI::HTTP.build({
            :host => 'selenium',
            :path => '/wd/hub',
            :port => 4444
        })
        @driver = Selenium::WebDriver.for(:remote, :url => selenium_uri.to_s, :desired_capabilities => :chrome)

        appli_url = URI::HTTP.build({
            :host => "172.17.42.1",
            :port => (ENV['APPLI_URL'].split ':').last.to_i
        }).to_s
        @driver.navigate.to appli_url
        @driver.manage.timeouts.implicit_wait = 5 # seconds
    end

    def test_add_simple_task
        name = @driver.find_element(:name, 'name')
        name.send_keys('some task')
        name.submit

        last_task_name = @driver.find_element(:css, '.task-name')
        assert_equal(last_task_name.text, 'some task')
    end
end

puts 'run acceptance test'
